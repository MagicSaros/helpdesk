package com.epam.controller;

import com.epam.converter.implementation.HistoryDtoConverter;
import com.epam.dto.HistoryDto;
import com.epam.entity.History;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.service.HistoryService;
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
@RequestMapping("/api/users/{userId}/tickets/{ticketId}/history")
@CrossOrigin
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private HistoryDtoConverter hIstoryDtoConverter;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<HistoryDto>> getHistoriesByTicketId(@PathVariable Long userId,
        @PathVariable Long ticketId) {
        List<HistoryDto> histories = historyService
            .getHistoriesByTicketId(ticketId)
            .stream()
            .map(history -> hIstoryDtoConverter.fromEntityToDto(history))
            .collect(Collectors.toList());
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HistoryDto> createHistoryAction(@PathVariable Long userId,
        @PathVariable Long ticketId, @Valid @RequestBody HistoryDto historyDto) {
        History history = hIstoryDtoConverter.fromDtoToEntity(historyDto);

        Ticket ticket = ticketService.getTicketById(ticketId);
        User user = userService.getUserById(userId);
        history.setTicket(ticket);
        history.setUser(user);

        history = historyService.addHistory(history);
        historyDto = hIstoryDtoConverter.fromEntityToDto(history);

        return new ResponseEntity<>(historyDto, HttpStatus.CREATED);
    }
}
