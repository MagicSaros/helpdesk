package com.epam.service;

import com.epam.entity.Attachment;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    Attachment getAttachmentById(Long id);

    Attachment addAttachment(Attachment attachment);

    List<Attachment> getAttachmentsByTicketId(Long id);

    void setFileAsBlob(Attachment attachment, MultipartFile file);

    byte[] getFileAsResource(Attachment attachment);

    Attachment removeAttachment(Long id);

    Attachment updateAttachment(Attachment attachment);
}
