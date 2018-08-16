package com.epam.controller;

import com.epam.converter.implementation.AttachmentDtoConverter;
import com.epam.dto.AttachmentDto;
import com.epam.entity.Attachment;
import com.epam.entity.Ticket;
import com.epam.exception.FileUploadException;
import com.epam.service.AttachmentService;
import com.epam.service.TicketService;
import java.sql.Blob;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/users/{userId}/tickets/{ticketId}/attachments")
@CrossOrigin
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private AttachmentDtoConverter attachmentDtoConverter;

    @PostMapping
    public ResponseEntity<AttachmentDto> createAttachment(@PathVariable Long userId,
        @PathVariable Long ticketId, @RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new FileUploadException(
                "File has not been chosen in the multipart form or the chosen file has no content");
        }

        String name = multipartFile.getOriginalFilename();
        Blob blob = null;
        try {
            blob = new SerialBlob(multipartFile.getBytes());
        } catch (Exception e) {
            throw new FileUploadException(e.getMessage());
        }
        Ticket ticket = ticketService.getTicketById(ticketId);

        Attachment attachment = new Attachment.Builder()
            .setName(name)
            .setBlob(blob)
            .setTicket(ticket)
            .build();
        attachment = attachmentService.addAttachment(attachment);
        AttachmentDto attachmentDto = attachmentDtoConverter.fromEntityToDto(attachment);
        return new ResponseEntity<>(attachmentDto, HttpStatus.CREATED);
    }
}
