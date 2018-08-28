package com.epam.repository;

import com.epam.entity.Feedback;
import java.util.Optional;

public interface FeedbackRepository {

    Feedback addFeedback(Feedback feedback);

    Optional<Feedback> getFeedbackByTicketId(Long id);
}
