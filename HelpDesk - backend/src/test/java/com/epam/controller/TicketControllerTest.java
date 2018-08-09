package com.epam.controller;

import com.epam.converter.implementation.TicketDtoConverter;
import com.epam.dto.TicketDto;
import com.epam.dto.UserDto;
import com.epam.entity.Category;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.UserRole;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {

    private static final String URL_PREFIX = "/api/users/{userId}/tickets";

    private List<Ticket> tickets = new LinkedList<>();

    private List<TicketDto> ticketsDto = new LinkedList<>();

    @Mock
    TicketDtoConverter ticketDtoConverter;

    @Mock
    TicketService ticketService;

    @Mock
    UserService userService;

    @InjectMocks
    TicketController ticketController = new TicketController();

    @Before
    public void init() {
        standaloneSetup(ticketController);

        User user = new User.Builder()
            .setId(1L)
            .setFirstName("First")
            .setLastName("Last")
            .setRole(UserRole.EMPLOYEE)
            .build();

        Ticket ticket = new Ticket.Builder()
            .setId(1L)
            .setName("Name")
            .setAssignee(user)
            .setOwner(user)
            .setApprover(user)
            .setCategory(new Category())
            .build();

        tickets.add(ticket);
        tickets.add(ticket);
        tickets.add(ticket);

        UserDto userDto = new UserDto.Builder()
            .setId(1L)
            .setFirstName("First")
            .setLastName("Last")
            .build();

        TicketDto ticketDto = new TicketDto.Builder()
            .setId(1L)
            .setName("Name")
            .setOwner(userDto)
            .build();

        ticketsDto.add(ticketDto);
        ticketsDto.add(ticketDto);
        ticketsDto.add(ticketDto);
    }

    @Test
    public void getAllTicketsTest() {
        String url = URL_PREFIX;

        Ticket ticket = tickets.get(0);
        User user = ticket.getOwner();

        TicketDto ticketDto = ticketsDto.get(0);
        UserDto userDto = ticketDto.getOwner();

        BDDMockito.given(userService.getUserById(1L)).willReturn(user);
        BDDMockito.given(ticketService.getTicketsByUser(user)).willReturn(tickets);
        BDDMockito.given(ticketDtoConverter.fromEntityToDto(ticket)).willReturn(ticketDto);

        given()
            .when()
            .get(url, 1)
            .then()
            .statusCode(200)
            .and()
            .body("", hasSize(3))
            .body("get(0).name", equalTo(ticketDto.getName()))
            .body("get(0).owner.firstName", equalTo(userDto.getFirstName()))
            .body("get(0).owner.lastName", equalTo(userDto.getLastName()));
    }

    @After
    public void clear() {
        tickets.clear();
    }
}
