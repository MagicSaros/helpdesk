package com.epam.controller;

import com.epam.converter.implementation.CommentDtoConverter;
import com.epam.converter.implementation.TicketDtoConverter;
import com.epam.dto.TicketDto;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.TicketAction;
import com.epam.exception.ApiError;
import com.epam.service.StateTransitionService;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/{userId}/tickets")
@CrossOrigin
@Api(value = "tickets", description = "Ticket API")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketDtoConverter ticketDtoConverter;

    @Autowired
    private CommentDtoConverter commentDtoConverter;

    @Autowired
    private StateTransitionService stateTransitionService;

    @GetMapping
    @ApiOperation(value = "Get tickets relevant to user", response = TicketDto.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = TicketDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<List<TicketDto>> getAllTickets(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<TicketDto> ticketsDto = ticketService.getTicketsByUser(user).stream()
            .map(ticketDtoConverter::fromEntityToDto)
            .collect(Collectors.toList());
        return new ResponseEntity<>(ticketsDto, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}")
    @ApiOperation(value = "Get a ticket by id", response = TicketDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = TicketDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<TicketDto> getTicket(@PathVariable Long userId,
        @PathVariable Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        TicketDto ticketDto = ticketDtoConverter.fromEntityToDto(ticket);
        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create a ticket", response = TicketDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Ticket created", response = TicketDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<TicketDto> createTicket(@PathVariable Long userId,
        @Valid @RequestBody TicketDto ticketDto) {
        Ticket ticket = ticketDtoConverter.fromDtoToEntity(ticketDto);

        User owner = userService.getUserById(userId);

        ticket = ticketService.addTicket(ticket, owner);
        ticketDto = ticketDtoConverter.fromEntityToDto(ticket);

        return new ResponseEntity<>(ticketDto, HttpStatus.CREATED);
    }

    @PutMapping("/{ticketId}")
    @ApiOperation(value = "Update a whole ticket", notes = "Usually using to update ticket data", response = TicketDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = TicketDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<TicketDto> editTicket(@PathVariable Long userId,
        @PathVariable Long ticketId, @Valid @RequestBody TicketDto ticketDto) {
        Ticket ticket = ticketDtoConverter.fromDtoToEntity(ticketDto);
        User user = userService.getUserById(userId);
        ticket = ticketService.updateTicket(ticket, user);
        ticketDto = ticketDtoConverter.fromEntityToDto(ticket);
        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @PatchMapping("/{ticketId}")
    @ApiOperation(value = "Update a ticket partly", notes = "Usually using to change ticket state", response = TicketDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = TicketDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<TicketDto> updateTicket(@PathVariable Long userId,
        @PathVariable Long ticketId, @Valid @RequestBody TicketDto ticketDto) {
        Ticket ticket = ticketDtoConverter.fromDtoToEntity(ticketDto);
        User user = userService.getUserById(userId);
        ticket = ticketService.updateTicket(ticket, user);
        ticketDto = ticketDtoConverter.fromEntityToDto(ticket);
        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}/actions")
    @ApiOperation(value = "Get a list of available actions for a ticket", response = TicketAction.class, responseContainer = "Set")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = TicketDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<Collection<TicketAction>> getAllowedActions(@PathVariable Long userId,
        @PathVariable Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        User user = userService.getUserById(userId);
        Collection<TicketAction> actions = stateTransitionService.getAllowedActions(ticket, user);
        return new ResponseEntity<>(actions, HttpStatus.OK);
    }
}
