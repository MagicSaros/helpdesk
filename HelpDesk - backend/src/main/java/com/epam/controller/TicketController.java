package com.epam.controller;

import com.epam.converter.implementation.TicketDtoConverter;
import com.epam.dto.TicketDto;
import com.epam.entity.User;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Autowired
    TicketDtoConverter ticketDtoConverter;

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets(@RequestParam(required = false, name = "my") String param) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<TicketDto> ticketsDto;
        if (param != null) {
            ticketsDto = ticketService.getTicketsByOwner(user).stream()
                    .map(ticket -> ticketDtoConverter.fromEntityToDto(ticket))
                    .collect(Collectors.toList());
        } else {
            ticketsDto = ticketService.getTicketsByUser(user).stream()
                    .map(ticket -> ticketDtoConverter.fromEntityToDto(ticket))
                    .collect(Collectors.toList());
        }
        return new ResponseEntity<>(ticketsDto, HttpStatus.OK);
    }
}
