package com.epam.controller;

import com.epam.converter.implementation.TicketDtoConverter;
import com.epam.dto.TicketDto;
import com.epam.dto.UserDto;
import com.epam.entity.Category;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.UserRole;
import com.epam.service.TicketService;
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

    private static final String URL_PREFIX = "/api/tickets";

    private List<Ticket> tickets = new LinkedList<>();

    private List<TicketDto> ticketsDto = new LinkedList<>();

    @Mock
    TicketDtoConverter ticketDtoConverter;

    @Mock
    TicketService ticketService;

    @InjectMocks
    TicketController ticketController = new TicketController();

    @Before
    public void init() {
        standaloneSetup(ticketController);

        User user = new User();
        user.setFirstName("First");
        user.setLastName("Last");
        user.setRole(UserRole.EMPLOYEE);

        Ticket ticket = new Ticket();
        ticket.setName("Name");
        ticket.setAssignee(user);
        ticket.setOwner(user);
        ticket.setApprover(user);
        ticket.setCategory(new Category());

        tickets.add(ticket);
        tickets.add(ticket);
        tickets.add(ticket);

        UserDto userDto = new UserDto();
        userDto.setFirstName("First");
        userDto.setLastName("Last");

        TicketDto ticketDto = new TicketDto();
        ticketDto.setName("Name");
        ticketDto.setOwner(userDto);

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

        BDDMockito.given(ticketService.getTicketsByUser(user)).willReturn(tickets);
        BDDMockito.given(ticketDtoConverter.fromEntityToDto(ticket)).willReturn(ticketDto);

        given()
                .when()
                .get(url)
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
