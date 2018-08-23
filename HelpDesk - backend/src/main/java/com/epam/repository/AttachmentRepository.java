package com.epam.repository;

import com.epam.entity.Attachment;
import java.util.List;
import java.util.Optional;

public interface AttachmentRepository {

    Attachment addAttachment(Attachment attachment);

    Optional<Attachment> getAttachmentById(Long id);

    byte[] getFileAsResource(Attachment attachment);

    List<Attachment> getAttachmentsByTicketId(Long id);

    Attachment removeAttachment(Long id);

    Attachment updateAttachment(Attachment attachment);
}
