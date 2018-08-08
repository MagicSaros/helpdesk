package com.epam.service;

import com.epam.entity.Ticket;
import com.epam.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TicketService {

    List<Ticket> getTicketsByUser(User user);

    List<Ticket> getTicketsByOwner(User owner);
}
