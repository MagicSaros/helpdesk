package com.epam.controller;

import com.epam.converter.implementation.AttachmentDtoConverter;
import com.epam.dto.AttachmentDto;
import com.epam.entity.Attachment;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.exception.ApiError;
import com.epam.service.AttachmentService;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/users/{userId}/tickets/{ticketId}/attachments")
@CrossOrigin
@Api(value = "attachments", description = "Attachment API")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private AttachmentDtoConverter attachmentDtoConverter;

    @GetMapping
    @ApiOperation(value = "Get ticket attachments", response = AttachmentDto.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = AttachmentDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<List<AttachmentDto>> getAttachmentsByTicketId(@PathVariable Long userId,
        @PathVariable Long ticketId) {
        List<AttachmentDto> attachments = attachmentService
            .getAttachmentsByTicketId(ticketId)
            .stream()
            .map(attachmentDtoConverter::fromEntityToDto)
            .collect(Collectors.toList());
        return new ResponseEntity<>(attachments, HttpStatus.OK);
    }

    @GetMapping("/{attachmentId}")
    @ApiOperation(value = "Get ticket attachment", response = AttachmentDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = AttachmentDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<AttachmentDto> getAttachment(@PathVariable Long userId,
        @PathVariable Long ticketId, @PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.getAttachmentById(attachmentId);
        AttachmentDto attachmentDto = attachmentDtoConverter.fromEntityToDto(attachment);
        return new ResponseEntity<>(attachmentDto, HttpStatus.OK);
    }

    @GetMapping("/{attachmentId}/download")
    @ApiOperation(value = "Download ticket attachment", response = byte[].class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Downloaded", response = byte[].class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
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
    @ApiOperation(value = "Create ticket attachment", response = AttachmentDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Attachment created", response = AttachmentDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<AttachmentDto> createAttachment(@PathVariable Long userId,
        @PathVariable Long ticketId, @RequestParam("file") MultipartFile multipartFile) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        User user = userService.getUserById(userId);

        Attachment attachment = attachmentService.addAttachment(ticket, user, multipartFile);
        AttachmentDto attachmentDto = attachmentDtoConverter.fromEntityToDto(attachment);
        return new ResponseEntity<>(attachmentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{attachmentId}")
    @ApiOperation(value = "Delete ticket attachment", response = AttachmentDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = AttachmentDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<AttachmentDto> deleteAttachment(@PathVariable Long userId,
        @PathVariable Long ticketId, @PathVariable Long attachmentId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        User user = userService.getUserById(userId);
        Attachment attachment = attachmentService.getAttachmentById(attachmentId);
        attachment = attachmentService.removeAttachment(ticket, user, attachment);
        AttachmentDto attachmentDto = attachmentDtoConverter.fromEntityToDto(attachment);
        return new ResponseEntity<>(attachmentDto, HttpStatus.OK);
    }
}
