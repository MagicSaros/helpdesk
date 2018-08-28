package com.epam.repository.implementation;

import com.epam.entity.Feedback;
import com.epam.repository.FeedbackRepository;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Feedback addFeedback(Feedback feedback) {
        Session session = sessionFactory.getCurrentSession();
        session.save(feedback);
        return feedback;
    }

    @Override
    public Optional<Feedback> getFeedbackByTicketId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Feedback> query = session
            .createQuery("from Feedback where ticket.id = :id", Feedback.class);
        query.setParameter("id", id);
        return query.uniqueResultOptional();
    }
}
