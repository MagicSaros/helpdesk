package com.epam.service;

import com.epam.entity.Attachment;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    Attachment getAttachmentById(Long id);

    Attachment addAttachment(Ticket ticket, User user, MultipartFile multipartFile);

    List<Attachment> getAttachmentsByTicketId(Long id);

    void setFileAsBlob(Attachment attachment, MultipartFile file);

    byte[] getFileAsResource(Attachment attachment);

    Attachment removeAttachment(Ticket ticket, User user, Attachment attachment);
}
