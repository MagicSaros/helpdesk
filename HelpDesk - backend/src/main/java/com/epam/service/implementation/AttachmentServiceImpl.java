package com.epam.service.implementation;

import com.epam.entity.Attachment;
import com.epam.exception.AttachmentNotFoundException;
import com.epam.exception.FileLoadingException;
import com.epam.repository.AttachmentRepository;
import com.epam.service.AttachmentService;
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

    @Override
    public Attachment getAttachmentById(Long id) {
        Optional<Attachment> attachment = attachmentRepository.getAttachmentById(id);
        return attachment.orElseThrow(
            () -> new AttachmentNotFoundException("Attachment not found by passed id"));
    }

    @Override
    public Attachment addAttachment(Attachment attachment) {
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
    public Attachment removeAttachment(Long id) {
        return attachmentRepository.removeAttachment(id);
    }

    @Override
    public Attachment updateAttachment(Attachment attachment) {
        return attachmentRepository.updateAttachment(attachment);
    }
}
