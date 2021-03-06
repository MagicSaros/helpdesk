package com.epam.controller;

import com.epam.converter.implementation.CommentDtoConverter;
import com.epam.dto.CommentDto;
import com.epam.entity.Comment;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.exception.ApiError;
import com.epam.service.CommentService;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/{userId}/tickets/{ticketId}/comments")
@CrossOrigin
@Api(value = "comments", description = "Comment API")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentDtoConverter commentDtoConverter;

    @GetMapping
    @ApiOperation(value = "Get all ticket comments", response = CommentDto.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = CommentDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<List<CommentDto>> getCommentsByTicketId(@PathVariable Long userId,
        @PathVariable Long ticketId) {
        List<CommentDto> comments = commentService
            .getCommentsByTicketId(ticketId)
            .stream()
            .map(commentDtoConverter::fromEntityToDto)
            .collect(Collectors.toList());
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create ticket comment", response = CommentDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Comment created", response = CommentDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<CommentDto> createComment(@PathVariable Long userId,
        @PathVariable Long ticketId, @Valid @RequestBody CommentDto commentDto) {
        Comment comment = commentDtoConverter.fromDtoToEntity(commentDto);

        User user = userService.getUserById(userId);
        Ticket ticket = ticketService.getTicketById(ticketId);

        comment = commentService.addComment(ticket, user, comment);
        commentDto = commentDtoConverter.fromEntityToDto(comment);

        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }
}
