package com.epam.component.implementation;

import com.epam.component.StateTransitionManager;
import com.epam.enums.State;
import com.epam.enums.TicketAction;
import com.epam.enums.UserRole;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StateTransitionManagerImpl implements StateTransitionManager {

    private Map<State, Map<TicketAction, State>> transitions = new EnumMap<>(State.class);

    private Map<State, Map<TicketAction, UserRole[]>> permissions = new EnumMap<>(State.class);

    public StateTransitionManagerImpl() {
    }

    private StateTransitionManagerImpl(Builder builder) {
        this.transitions = builder.transitions;
        this.permissions = builder.permissions;
    }

    @Override
    public Collection<TicketAction> getAllowedActions(State currentState, UserRole role) {
        if (transitions.containsKey(currentState)) {
            return transitions
                .get(currentState)
                .keySet()
                .stream()
                .filter(action -> isActionForRoleAllowed(currentState, action, role))
                .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Override
    public TicketAction getAction(State currentState, State newState, UserRole role) {
        TicketAction ticketAction = null;
        if (transitions.containsKey(currentState)) {
            Map<TicketAction, State> actions = transitions.get(currentState);
            Collection<TicketAction> allowedActions = getAllowedActions(currentState, role);
            List<TicketAction> ticketActionList = allowedActions
                .stream()
                .filter(action -> actions.get(action) == newState)
                .collect(Collectors.toList());
            if (!ticketActionList.isEmpty()) {
                ticketAction = ticketActionList.get(0);
            }
        }
        return ticketAction;
    }

    @Override
    public State getNewState(State currentState, TicketAction action, UserRole role) {
        Collection<TicketAction> allowedActions = getAllowedActions(currentState, role);
        if (allowedActions.contains(action)) {
            return transitions.get(currentState).get(action);
        }
        return null;
    }

    @Override
    public boolean isActionExist(State currentState, State newState, UserRole role) {
        return getAction(currentState, newState, role) != null;
    }

    private boolean isActionAllowed(State currentState, TicketAction action, UserRole role) {
        Collection<TicketAction> allowedActions = getAllowedActions(currentState, role);
        return allowedActions.contains(action);
    }

    private boolean isActionForRoleAllowed(State currentState, TicketAction action, UserRole role) {
        UserRole[] roles = permissions.get(currentState).get(action);
        for (UserRole allowed : roles) {
            if (allowed == role) {
                return true;
            }
        }
        return false;
    }

    public static class Builder {

        private Map<State, Map<TicketAction, State>> transitions = new EnumMap<>(State.class);

        private Map<State, Map<TicketAction, UserRole[]>> permissions = new EnumMap<>(State.class);

        public StateTransition addStateTransition(State currentState,
            TicketAction action, State newState) {
            if (!transitions.containsKey(currentState)) {
                transitions.put(currentState, new EnumMap<>(TicketAction.class));
            }
            transitions.get(currentState).put(action, newState);
            return new StateTransition(this, currentState, action);
        }

        public Builder removeStateTransition(State currentState,
            TicketAction action) {
            transitions.get(currentState).remove(action);
            return this;
        }

        public StateTransitionManagerImpl build() {
            return new StateTransitionManagerImpl(this);
        }

        public class StateTransition {

            private Builder builder;

            private State currentState;

            private TicketAction action;

            StateTransition(Builder builder, State currentState, TicketAction action) {
                this.builder = builder;
                this.currentState = currentState;
                this.action = action;
            }

            public Builder addPermission(UserRole... userRoles) {
                if (!permissions.containsKey(currentState)) {
                    permissions.put(currentState, new EnumMap<>(TicketAction.class));
                }
                permissions.get(currentState).put(action, userRoles);
                return builder;
            }
        }
    }
}
