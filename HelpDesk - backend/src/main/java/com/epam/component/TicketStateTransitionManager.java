package com.epam.component;

import com.epam.enums.State;
import com.epam.enums.TicketAction;
import com.epam.enums.UserRole;
import java.util.Collection;

public interface TicketStateTransitionManager {

    Collection<TicketAction> getAllowedActions(State currentState, UserRole role);

    TicketAction getAction(State currentState, State newState, UserRole role);

    State getNewState(State currentState, TicketAction action, UserRole role);

    boolean isActionExist(State currentState, State newState, UserRole role);
}
