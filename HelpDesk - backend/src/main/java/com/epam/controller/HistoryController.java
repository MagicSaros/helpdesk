package com.epam.controller;

import com.epam.converter.implementation.HistoryDtoConverter;
import com.epam.dto.HistoryDto;
import com.epam.entity.History;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.exception.ApiError;
import com.epam.service.HistoryService;
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
@RequestMapping("/api/users/{userId}/tickets/{ticketId}/history")
@CrossOrigin
@Api(value = "histories", description = "History API")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private HistoryDtoConverter historyDtoConverter;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation(value = "Get ticket history", response = HistoryDto.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = HistoryDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<List<HistoryDto>> getHistoriesByTicketId(@PathVariable Long userId,
        @PathVariable Long ticketId) {
        List<HistoryDto> histories = historyService
            .getHistoriesByTicketId(ticketId)
            .stream()
            .map(historyDtoConverter::fromEntityToDto)
            .collect(Collectors.toList());
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create history record", response = HistoryDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "History created", response = HistoryDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<HistoryDto> createHistoryAction(@PathVariable Long userId,
        @PathVariable Long ticketId, @Valid @RequestBody HistoryDto historyDto) {
        History history = historyDtoConverter.fromDtoToEntity(historyDto);

        Ticket ticket = ticketService.getTicketById(ticketId);
        User user = userService.getUserById(userId);
        history.setTicket(ticket);
        history.setUser(user);

        history = historyService.addHistory(history);
        historyDto = historyDtoConverter.fromEntityToDto(history);

        return new ResponseEntity<>(historyDto, HttpStatus.CREATED);
    }
}
