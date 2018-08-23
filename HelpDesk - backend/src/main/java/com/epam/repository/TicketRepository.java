package com.epam.repository;

import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.State;
import com.epam.enums.UserRole;
import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    List<Ticket> getAllTickets();

    Optional<Ticket> getTicketById(Long id);

    List<Ticket> getTicketsByOwner(User owner);

    List<Ticket> getTicketsByOwnerAndState(User owner, State state);

    List<Ticket> getTicketsByApprover(User approver);

    List<Ticket> getTicketsByAssignee(User assignee);

    List<Ticket> getTicketsByRole(UserRole role);

    List<Ticket> getTicketsByRoleAndState(UserRole role, State state);

    List<Ticket> getTicketsByState(State state);

    Ticket addTicket(Ticket ticket);

    Ticket updateTicket(Ticket ticket);
}
