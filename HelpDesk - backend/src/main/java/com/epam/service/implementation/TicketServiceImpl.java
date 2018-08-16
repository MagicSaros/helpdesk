package com.epam.service.implementation;

import com.epam.entity.Category;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.State;
import com.epam.enums.UserRole;
import com.epam.exception.TicketNotFoundException;
import com.epam.repository.TicketRepository;
import com.epam.service.CategoryService;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.getTicketById(id)
            .orElseThrow(() -> new TicketNotFoundException("Ticket not found by passed id"));
    }

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
                tickets.addAll(
                    ticketRepository.getTicketsByRoleAndState(UserRole.EMPLOYEE, State.NEW));
                tickets.addAll(ticketRepository.getTicketsByApprover(user));
                break;
            case ENGINEER:
                tickets.addAll(ticketRepository.getTicketsByState(State.APPROVED));
                tickets.addAll(ticketRepository.getTicketsByAssignee(user));
                break;
        }
        return tickets.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        ticket.setCreatedOn(new Date());
        ticket.setAssignee(null);
        ticket.setApprover(null);

        Category category = ticket.getCategory();
        Long categoryId = category.getId();
        category = categoryService.getCategoryById(categoryId);
        ticket.setCategory(category);

        return ticketRepository.addTicket(ticket);
    }
}
