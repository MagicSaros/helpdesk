package com.epam.controller;

import com.epam.converter.implementation.FeedbackDtoConverter;
import com.epam.dto.FeedbackDto;
import com.epam.entity.Feedback;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.exception.ApiError;
import com.epam.service.FeedbackService;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
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
@Api(value = "feedbacks", description = "Feedback API")
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
    @ApiOperation(value = "Get ticket feedback", response = FeedbackDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = FeedbackDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<FeedbackDto> getFeedbackByTicket(@PathVariable Long userId,
        @PathVariable Long ticketId) {
        Feedback feedback = feedbackService.getFeedbackByTicketId(ticketId);
        FeedbackDto feedbackDto = feedbackDtoConverter.fromEntityToDto(feedback);
        return new ResponseEntity<>(feedbackDto, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create ticket feedback", response = FeedbackDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Feedback created", response = FeedbackDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
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
