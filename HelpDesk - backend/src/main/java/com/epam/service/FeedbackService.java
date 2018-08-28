package com.epam.service;

import com.epam.entity.Feedback;
import com.epam.entity.Ticket;
import com.epam.entity.User;

public interface FeedbackService {

    Feedback addFeedback(Ticket ticket, User user, Feedback feedback);

    Feedback getFeedbackByTicketId(Long id);
}
