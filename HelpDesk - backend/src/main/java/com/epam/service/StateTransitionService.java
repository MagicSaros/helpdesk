package com.epam.service;

import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.State;
import com.epam.enums.TicketAction;
import java.util.Collection;

public interface StateTransitionService {

    Collection<TicketAction> getAllowedActions(Ticket ticket, User user);

    void transitTicketState(Ticket ticket, User user, State newState);
}
