package com.epam.service;

import static org.junit.Assert.assertEquals;

import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.UserRole;
import com.epam.repository.TicketRepository;
import com.epam.service.implementation.TicketServiceImpl;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    private List<Ticket> tickets;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService = new TicketServiceImpl();

    @Before
    public void init() {
        tickets = new LinkedList<>();
        User manager = new User.Builder()
            .setId(1L)
            .setEmail("manager")
            .setRole(UserRole.MANAGER)
            .build();

        User employee = new User.Builder()
            .setId(2L)
            .setEmail("employee")
            .setRole(UserRole.EMPLOYEE)
            .build();

        User engineer = new User.Builder()
            .setId(3L)
            .setEmail("engineer")
            .setRole(UserRole.ENGINEER)
            .build();

        Ticket ticket1 = new Ticket.Builder()
            .setId(1L)
            .setName("ticket1")
            .setOwner(employee)
            .setApprover(manager)
            .setAssignee(engineer)
            .build();
        Ticket ticket2 = new Ticket.Builder()
            .setId(1L)
            .setName("ticket2")
            .setOwner(manager)
            .setApprover(manager)
            .setAssignee(engineer)
            .build();
        Ticket ticket3 = new Ticket.Builder()
            .setId(1L)
            .setName("ticket3")
            .setOwner(employee)
            .setApprover(manager)
            .setAssignee(engineer)
            .build();

        tickets.add(ticket1);
        tickets.add(ticket2);
        tickets.add(ticket3);
    }

    @Test
    public void getTicketByIdTest() {
        Long id = 1L;

        Ticket expected = tickets.get(0);
        BDDMockito.given(ticketRepository.getTicketById(id)).willReturn(Optional.of(expected));
        Ticket actual = ticketService.getTicketById(id);

        assertEquals(expected, actual);

        id = 2L;

        expected = tickets.get(0);
        BDDMockito.given(ticketRepository.getTicketById(id)).willReturn(Optional.of(expected));
        actual = ticketService.getTicketById(id);

        assertEquals(expected, actual);
    }

    @Test
    public void getTicketByOwnerTest() {
        User employee = tickets.get(0).getOwner();
        User manager = tickets.get(1).getOwner();

        List<Ticket> expectedManagerTickets = tickets
            .stream()
            .filter(ticket -> ticket.getOwner().equals(manager))
            .collect(Collectors.toList());

        List<Ticket> expectedEmployeeTickets = tickets
            .stream()
            .filter(ticket -> ticket.getOwner().equals(employee))
            .collect(Collectors.toList());

        BDDMockito.given(ticketRepository.getTicketsByOwner(manager))
            .willReturn(expectedManagerTickets);
        BDDMockito.given(ticketRepository.getTicketsByOwner(employee))
            .willReturn(expectedEmployeeTickets);

        List<Ticket> actualManagerTickets = ticketService.getTicketsByOwner(manager);
        List<Ticket> actualEmployeeTickets = ticketService.getTicketsByOwner(employee);

        assertEquals(expectedManagerTickets.size(), actualManagerTickets.size());
        assertEquals(expectedEmployeeTickets.size(), actualEmployeeTickets.size());

        assertEquals(expectedManagerTickets, actualManagerTickets);
        assertEquals(expectedEmployeeTickets, actualEmployeeTickets);
    }

    @After
    public void clear() {
        tickets.clear();
    }
}
