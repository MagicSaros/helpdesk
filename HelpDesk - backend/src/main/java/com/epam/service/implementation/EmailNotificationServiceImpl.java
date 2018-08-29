package com.epam.service.implementation;

import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.State;
import com.epam.exception.EmailNotificationException;
import com.epam.service.EmailNotificationService;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private static final String BASE_LINK = "http://localhost:3000/tickets/";

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void notifyUser(Ticket ticket, User recipient, State newState) {
        SimpleMailMessage simpleEmailMessage = getSimpleEmailMessage(ticket, recipient, newState);
        if (simpleEmailMessage != null) {
            MimeMessage mimeMessage = getMimeEmailMessage(simpleEmailMessage, recipient);

            Runnable sender = () -> mailSender.send(mimeMessage);

            Thread thread = new Thread(sender);
            thread.start();
        }
    }

    private MimeMessage getMimeEmailMessage(SimpleMailMessage message, User recipient) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessage.setContent(message.getText(), "text/html");
            helper.setSubject(message.getSubject());
            helper.setTo(recipient.getEmail());

            return mimeMessage;
        } catch (MessagingException e) {
            throw new EmailNotificationException(e.getMessage());
        }
    }

    private SimpleMailMessage getSimpleEmailMessage(Ticket ticket, User recipient, State newState) {
        Long id = ticket.getId();
        String link = BASE_LINK + id;
        String firstName = recipient.getFirstName();
        String lastName = recipient.getLastName();
        State currentState = ticket.getState();
        String subject = null;
        String text = null;
        switch (currentState) {
            case DRAFT:
            case DECLINED:
                if (newState == State.NEW) {
                    subject = "New ticket for approval";
                    text = "Dear Managers,<br><br>"
                        + String.format(
                        "New ticket <a href=\"%s\">#%s</a> is waiting for your approval.", link,
                        id);
                }
                break;
            case NEW:
                switch (newState) {
                    case APPROVED:
                        subject = "Ticket was approved";
                        text = "Dear Users,<br><br>"
                            + String
                            .format("Ticket <a href=\"%s\">#%s</a> was approved by the Manager.",
                                link, id);
                        break;
                    case DECLINED:
                        subject = "Ticket was declined";
                        text = String.format("Dear %s %s,<br><br>", firstName,
                            lastName)
                            + String.format("Ticket <a href=\"%s\">#%s</a> was declined by the Manager.", link, id);
                        break;
                    case CANCELLED:
                        subject = "Ticket was cancelled";
                        text = String.format("Dear %s %s,<br><br>", firstName,
                            lastName)
                            + String
                            .format("Ticket <a href=\"%s\">#%s</a> was cancelled by the Manager.",
                                link, id);
                        break;
                    default:
                        break;
                }
                break;
            case APPROVED:
                if (newState == State.CANCELLED) {
                    subject = "Ticket was cancelled";
                    text = "Dear Users,<br><br>"
                        + String
                        .format("Ticket <a href=\"%s\">#%s</a> was cancelled by the Engineer.",
                            link, id);
                }
                break;
            case IN_PROGRESS:
                if (newState == State.DONE) {
                    subject = "Ticket was done";
                    text = String
                        .format("Dear %s %s,<br><br>", firstName, lastName)
                        + String
                        .format("Ticket <a href=\"%s\">#%s</a> was done by the Engineer.%n", link,
                            id)
                        + "Please provide your feedback clicking on the ticket ID.";
                }
                break;
            case DONE:
                if (newState == State.DONE) {
                    subject = "Feedback was provided";
                    text = String
                        .format("Dear %s %s,<br><br>", firstName, lastName)
                        + String
                        .format("The feedback was provided on ticket <a href=\"%s\">#%s</a>.", link,
                            id);
                }
                break;
            default:
                break;
        }

        if (subject == null) {
            return null;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
