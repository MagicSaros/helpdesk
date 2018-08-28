package com.epam.controller;

import com.epam.converter.implementation.FeedbackDtoConverter;
import com.epam.dto.FeedbackDto;
import com.epam.entity.Feedback;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.service.FeedbackService;
import com.epam.service.TicketService;
import com.epam.service.UserService;
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
@RequestMapping("/api/users/{userId}/tickets/{ticketId}/feedback")
@CrossOrigin
public class FeedbackController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackDtoConverter feedbackDtoConverter;

    @GetMapping
    public ResponseEntity<FeedbackDto> getFeedbackByTicket(@PathVariable Long userId,
        @PathVariable Long ticketId) {
        Feedback feedback = feedbackService.getFeedbackByTicketId(ticketId);
        FeedbackDto feedbackDto = feedbackDtoConverter.fromEntityToDto(feedback);
        return new ResponseEntity<>(feedbackDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FeedbackDto> createFeedback(@PathVariable Long userId,
        @PathVariable Long ticketId, @Valid @RequestBody FeedbackDto feedbackDto) {
        Feedback feedback = feedbackDtoConverter.fromDtoToEntity(feedbackDto);
        Ticket ticket = ticketService.getTicketById(ticketId);
        User user = userService.getUserById(userId);
        feedback = feedbackService.addFeedback(ticket, user, feedback);
        feedbackDto = feedbackDtoConverter.fromEntityToDto(feedback);
        return new ResponseEntity<>(feedbackDto, HttpStatus.CREATED);
    }
}
