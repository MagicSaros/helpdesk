package com.epam.service.implementation;

import com.epam.component.TicketStateTransitionManager;
import com.epam.entity.History;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.State;
import com.epam.enums.TicketAction;
import com.epam.enums.UserRole;
import com.epam.exception.ImpermissibleActionException;
import com.epam.service.EmailNotificationService;
import com.epam.service.HistoryService;
import com.epam.service.StateTransitionService;
import com.epam.service.UserService;
import java.lang.reflect.Method;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateTransitionServiceImpl implements StateTransitionService {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TicketStateTransitionManager transitionManager;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private UserService userService;

    @Override
    public Collection<TicketAction> getAllowedActions(Ticket ticket, User user) {
        State currentState = ticket.getState();
        UserRole role = user.getRole();

        return transitionManager.getAllowedActions(currentState, role);
    }

    @Override
    public void transitTicketState(Ticket ticket, User user, State newState) {
        State currentState = ticket.getState();
        UserRole role = user.getRole();

        if (!isTransitionAllowed(currentState, newState, role)) {
            throw new ImpermissibleActionException("State transition is not allowed");
        }

        assigneeUserToStateTransition(ticket, user, newState);
        provideNotification(ticket, user, newState);
        ticket.setState(newState);

        if (currentState != newState) {
            History history = new History.Builder()
                .setUser(user)
                .setTicket(ticket)
                .setAction("Ticket Status is changed")
                .setDescription(
                    "Ticket Status is changed from " + currentState.toString() + " to " + newState
                        .toString())
                .build();
            historyService.addHistory(history);
        }
    }

    private void provideNotification(Ticket ticket, User user, State newState) {
        State currentState = ticket.getState();
        User owner = ticket.getOwner();
        User approver = ticket.getApprover();
        TicketAction action = transitionManager.getAction(currentState, newState, user.getRole());
        switch (action) {
            case SUBMIT:
                userService
                    .getUsersByRole(UserRole.MANAGER)
                    .forEach(
                        manager -> emailNotificationService.notifyUser(ticket, manager, newState));
                break;
            case APPROVE:
                userService
                    .getUsersByRole(UserRole.ENGINEER)
                    .forEach(engineer -> emailNotificationService
                        .notifyUser(ticket, engineer, newState));
                emailNotificationService.notifyUser(ticket, owner, newState);
                break;
            case DECLINE:
                emailNotificationService.notifyUser(ticket, owner, newState);
                break;
            case CANCEL:
                emailNotificationService.notifyUser(ticket, owner, newState);
                if (currentState == State.APPROVED) {
                    emailNotificationService.notifyUser(ticket, approver, newState);
                }
                break;
            case DONE:
                emailNotificationService.notifyUser(ticket, owner, newState);
                break;
            default:
                break;
        }
    }

    private boolean isTransitionAllowed(State currentState, State newState, UserRole role) {
        return transitionManager.isActionExist(currentState, newState, role);
    }

    private void assigneeUserToStateTransition(Ticket ticket, User user, State newState) {
        State currentState = ticket.getState();
        UserRole role = user.getRole();
        TicketAction action = defineTicketAction(currentState, newState, role);

        try {
            Method method = getMethodForUserSetting(currentState, action);
            if (method != null) {
                method.invoke(ticket, user);
            }
        } catch (Exception e) {
            throw new ImpermissibleActionException("Impossible to update ticket with user");
        }
    }

    private TicketAction defineTicketAction(State currentState, State newState, UserRole role) {
        return transitionManager.getAction(currentState, newState, role);
    }

    private Method getMethodForUserSetting(State currentState, TicketAction action)
        throws NoSuchMethodException {
        Method method = null;
        switch (currentState) {
            case NEW:
                method = Ticket.class.getMethod("setApprover", User.class);
                break;
            case APPROVED:
                if (action == TicketAction.ASSIGN) {
                    method = Ticket.class.getMethod("setAssignee", User.class);
                }
                break;
            case DRAFT:
                method = Ticket.class.getMethod("setOwner", User.class);
                break;
            default:
                break;
        }
        return method;
    }
}
