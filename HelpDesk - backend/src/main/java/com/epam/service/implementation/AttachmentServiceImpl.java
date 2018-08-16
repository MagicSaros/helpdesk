package com.epam.service.implementation;

import com.epam.entity.Attachment;
import com.epam.repository.AttachmentRepository;
import com.epam.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public Attachment getAttachmentById(Long id) {
        return null;
    }

    @Override
    public Attachment addAttachment(Attachment attachment) {
        return attachmentRepository.addAttachment(attachment);
    }
}
