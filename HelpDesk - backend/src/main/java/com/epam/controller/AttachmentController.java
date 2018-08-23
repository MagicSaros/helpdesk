package com.epam.controller;

import com.epam.converter.implementation.AttachmentDtoConverter;
import com.epam.dto.AttachmentDto;
import com.epam.entity.Attachment;
import com.epam.entity.Ticket;
import com.epam.service.AttachmentService;
import com.epam.service.TicketService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping
    public ResponseEntity<List<AttachmentDto>> getAttachmentsByTicketId(@PathVariable Long userId,
        @PathVariable Long ticketId) {
        List<AttachmentDto> attachments = attachmentService
            .getAttachmentsByTicketId(ticketId)
            .stream()
            .map(attachment -> attachmentDtoConverter.fromEntityToDto(attachment))
            .collect(Collectors.toList());
        return new ResponseEntity<>(attachments, HttpStatus.OK);
    }

    @GetMapping("/{attachmentId}")
    public ResponseEntity<AttachmentDto> getAttachment(@PathVariable Long userId,
        @PathVariable Long ticketId, @PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.getAttachmentById(attachmentId);
        AttachmentDto attachmentDto = attachmentDtoConverter.fromEntityToDto(attachment);
        return new ResponseEntity<>(attachmentDto, HttpStatus.OK);
    }

    @GetMapping("/{attachmentId}/download")
    private ResponseEntity<byte[]> getFileFromAttachment(@PathVariable Long userId,
        @PathVariable Long ticketId, @PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.getAttachmentById(attachmentId);

        byte[] bytes = attachmentService.getFileAsResource(attachment);

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .contentLength(bytes.length)
            .body(bytes);
    }

    @PostMapping
    public ResponseEntity<AttachmentDto> createAttachment(@PathVariable Long userId,
        @PathVariable Long ticketId, @RequestParam("file") MultipartFile multipartFile) {
        Ticket ticket = ticketService.getTicketById(ticketId);

        Attachment attachment = new Attachment.Builder()
            .setTicket(ticket)
            .build();
        attachmentService.setFileAsBlob(attachment, multipartFile);

        attachment = attachmentService.addAttachment(attachment);
        AttachmentDto attachmentDto = attachmentDtoConverter.fromEntityToDto(attachment);
        return new ResponseEntity<>(attachmentDto, HttpStatus.CREATED);
    }

    @PutMapping("/{attachmentId}")
    public ResponseEntity<AttachmentDto> updateFile(@PathVariable Long userId,
        @PathVariable Long ticketId, @PathVariable Long attachmentId,
        @RequestParam("file") MultipartFile multipartFile) {
        Attachment attachment = attachmentService.getAttachmentById(attachmentId);
        attachmentService.setFileAsBlob(attachment, multipartFile);
        attachmentService.updateAttachment(attachment);
        AttachmentDto attachmentDto = attachmentDtoConverter.fromEntityToDto(attachment);
        return new ResponseEntity<>(attachmentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<AttachmentDto> deleteAttachment(@PathVariable Long userId,
        @PathVariable Long ticketId, @PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.removeAttachment(attachmentId);
        AttachmentDto attachmentDto = attachmentDtoConverter.fromEntityToDto(attachment);
        return new ResponseEntity<>(attachmentDto, HttpStatus.OK);
    }
}
