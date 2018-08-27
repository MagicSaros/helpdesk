package com.epam.service.implementation;

import com.epam.entity.Attachment;
import com.epam.entity.History;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.exception.AttachmentNotFoundException;
import com.epam.exception.FileLoadingException;
import com.epam.repository.AttachmentRepository;
import com.epam.service.AttachmentService;
import com.epam.service.HistoryService;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private HistoryService historyService;

    @Override
    public Attachment getAttachmentById(Long id) {
        Optional<Attachment> attachment = attachmentRepository.getAttachmentById(id);
        return attachment.orElseThrow(
            () -> new AttachmentNotFoundException("Attachment not found by passed id"));
    }

    @Override
    public Attachment addAttachment(Ticket ticket, User user, MultipartFile multipartFile) {
        Attachment attachment = new Attachment.Builder()
            .setTicket(ticket)
            .build();
        setFileAsBlob(attachment, multipartFile);

        History history = new History.Builder()
            .setUser(user)
            .setTicket(ticket)
            .setAction("File is attached")
            .setDescription("File is attached: " + attachment.getName())
            .build();
        historyService.addHistory(history);

        return attachmentRepository.addAttachment(attachment);
    }

    @Override
    public List<Attachment> getAttachmentsByTicketId(Long id) {
        return attachmentRepository.getAttachmentsByTicketId(id);
    }

    @Override
    public byte[] getFileAsResource(Attachment attachment) {
        return attachmentRepository.getFileAsResource(attachment);
    }

    @Override
    public void setFileAsBlob(Attachment attachment, MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileLoadingException(
                "File has not been chosen in the multipart form or the chosen file has no content");
        }

        String name = file.getOriginalFilename();
        Blob blob;
        try {
            blob = new SerialBlob(file.getBytes());
        } catch (Exception e) {
            throw new FileLoadingException(e.getMessage());
        }

        attachment.setName(name);
        attachment.setBlob(blob);
    }

    @Override
    public Attachment removeAttachment(Ticket ticket, User user, Attachment attachment) {
        History history = new History.Builder()
            .setUser(user)
            .setTicket(ticket)
            .setAction("File is removed")
            .setDescription("File is removed: " + attachment.getName())
            .build();
        historyService.addHistory(history);

        return attachmentRepository.removeAttachment(attachment.getId());
    }
}
