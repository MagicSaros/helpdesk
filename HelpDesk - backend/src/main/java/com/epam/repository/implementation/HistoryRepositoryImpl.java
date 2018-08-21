package com.epam.repository.implementation;

import com.epam.entity.History;
import com.epam.repository.HistoryRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public History addHistory(History history) {
        Session session = sessionFactory.getCurrentSession();
        session.save(history);
        return history;
    }

    @Override
    public List<History> getHistoriesByTicketId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<History> query = session
            .createQuery("from History as h where ticket.id = :id order by h.date desc",
                History.class);
        query.setParameter("id", id);
        return query.list();
    }
}
