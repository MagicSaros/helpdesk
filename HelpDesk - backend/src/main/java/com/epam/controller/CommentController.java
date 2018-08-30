package com.epam.controller;

import com.epam.converter.implementation.CommentDtoConverter;
import com.epam.dto.CommentDto;
import com.epam.entity.Comment;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.service.CommentService;
import com.epam.service.TicketService;
import com.epam.service.UserService;
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
    public ResponseEntity<CommentDto> createComment(@PathVariable Long userId,
        @PathVariable Long ticketId, @Valid @RequestBody CommentDto commentDto) {
        Comment comment = commentDtoConverter.fromDtoToEntity(commentDto);

        User user = userService.getUserById(userId);
        Ticket ticket = ticketService.getTicketById(ticketId);
        comment.setUser(user);
        comment.setTicket(ticket);

        comment = commentService.addComment(comment);
        commentDto = commentDtoConverter.fromEntityToDto(comment);

        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }
}
