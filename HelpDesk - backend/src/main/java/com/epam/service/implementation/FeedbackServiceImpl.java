package com.epam.service.implementation;

import com.epam.entity.Feedback;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.State;
import com.epam.exception.ImpermissibleActionException;
import com.epam.repository.FeedbackRepository;
import com.epam.service.FeedbackService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback addFeedback(Ticket ticket, User user, Feedback feedback) {
        if (!ticket.getOwner().equals(user)) {
            throw new ImpermissibleActionException("Only ticket owner is able to leave feedback");
        }

        if (ticket.getState() != State.DONE) {
            throw new ImpermissibleActionException("Only ticket in state DONE is able to has feedback");
        }
        feedback.setUser(user);
        feedback.setTicket(ticket);
        feedback.setDate(new Date());
        return feedbackRepository.addFeedback(feedback);
    }

    @Override
    public Feedback getFeedbackByTicketId(Long id) {
        return feedbackRepository.getFeedbackByTicketId(id).orElse(null);
    }
}
