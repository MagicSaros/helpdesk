package com.epam.service.implementation;

import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.State;
import com.epam.enums.UserRole;
import com.epam.repository.TicketRepository;
import com.epam.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<Ticket> getTicketsByOwner(User owner) {
        return ticketRepository.getTicketsByOwner(owner);
    }

    @Override
    public List<Ticket> getTicketsByUser(User user) {
        List<Ticket> tickets = new LinkedList<>();
        switch (user.getRole()) {
            case EMPLOYEE:
                tickets.addAll(ticketRepository.getTicketsByOwner(user));
                break;
            case MANAGER:
                tickets.addAll(ticketRepository.getTicketsByOwner(user));
                tickets.addAll(ticketRepository.getTicketsByRoleAndState(UserRole.EMPLOYEE, State.NEW));
                tickets.addAll(ticketRepository.getTicketsByApprover(user));
                break;
            case ENGINEER:
                tickets.addAll(ticketRepository.getTicketsByState(State.APPROVED));
                tickets.addAll(ticketRepository.getTicketsByAssignee(user));
                break;
        }
        return tickets.stream().distinct().collect(Collectors.toList());
    }
}
