package com.epam.service;

import com.epam.entity.Attachment;

public interface AttachmentService {

    Attachment getAttachmentById(Long id);

    Attachment addAttachment(Attachment attachment);
}
