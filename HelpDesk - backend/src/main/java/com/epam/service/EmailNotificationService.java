package com.epam.service;

import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.State;

public interface EmailNotificationService {

    void notifyUser(Ticket ticket, User user, State newState);
}
