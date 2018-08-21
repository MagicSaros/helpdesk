package com.epam.controller;

import com.epam.converter.implementation.CommentDtoConverter;
import com.epam.converter.implementation.TicketDtoConverter;
import com.epam.dto.TicketDto;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import java.util.Date;
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
@RequestMapping("/api/users/{userId}/tickets")
@CrossOrigin
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketDtoConverter ticketDtoConverter;

    @Autowired
    private CommentDtoConverter commentDtoConverter;

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<TicketDto> ticketsDto = ticketService.getTicketsByUser(user).stream()
            .map(ticket -> ticketDtoConverter.fromEntityToDto(ticket))
            .collect(Collectors.toList());
        return new ResponseEntity<>(ticketsDto, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable Long userId, @PathVariable Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        TicketDto ticketDto = ticketDtoConverter.fromEntityToDto(ticket);
        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TicketDto> createTicket(@PathVariable Long userId,
        @Valid @RequestBody TicketDto ticketDto) {
        Ticket ticket = ticketDtoConverter.fromDtoToEntity(ticketDto);

        User owner = userService.getUserById(userId);
        ticket.setOwner(owner);
        ticket.setCreatedOn(new Date());

        ticket = ticketService.addTicket(ticket);
        ticketDto = ticketDtoConverter.fromEntityToDto(ticket);

        return new ResponseEntity<>(ticketDto, HttpStatus.CREATED);
    }
}
