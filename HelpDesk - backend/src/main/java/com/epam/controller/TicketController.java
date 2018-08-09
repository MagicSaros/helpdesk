package com.epam.controller;

import com.epam.converter.implementation.TicketDtoConverter;
import com.epam.dto.TicketDto;
import com.epam.entity.User;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/{userId}/tickets")
@CrossOrigin
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Autowired
    TicketDtoConverter ticketDtoConverter;

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<TicketDto> ticketsDto = ticketService.getTicketsByUser(user).stream()
            .map(ticket -> ticketDtoConverter.fromEntityToDto(ticket))
            .collect(Collectors.toList());
        return new ResponseEntity<>(ticketsDto, HttpStatus.OK);
    }
}
