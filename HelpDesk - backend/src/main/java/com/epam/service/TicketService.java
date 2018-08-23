package com.epam.service;

import com.epam.entity.Ticket;
import com.epam.entity.User;
import java.util.List;
import javax.transaction.Transactional;

@Transactional
public interface TicketService {

    List<Ticket> getTicketsByUser(User user);

    Ticket getTicketById(Long id);

    List<Ticket> getTicketsByOwner(User owner);

    Ticket addTicket(Ticket ticket);

    Ticket updateTicket(Ticket ticket);
}
