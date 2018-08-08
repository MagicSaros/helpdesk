package com.epam.repository.implementation;

import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.State;
import com.epam.enums.UserRole;
import com.epam.repository.TicketRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Ticket> getAllTickets() {
        Session session = sessionFactory.getCurrentSession();
        Query<Ticket> query = session.createQuery("from Ticket", Ticket.class);
        return query.list();
    }

    public List<Ticket> getTicketsByOwner(User owner) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ticket> query = session.createQuery("from Ticket where owner = :owner", Ticket.class);
        query.setParameter("owner", owner);
        return query.list();
    }

    @Override
    public List<Ticket> getTicketsByOwnerAndState(User owner, State state) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ticket> query = session.createQuery("from Ticket where owner = :owner and state = :state", Ticket.class);
        query.setParameter("owner", owner);
        query.setParameter("state", state);
        return query.list();
    }

    @Override
    public List<Ticket> getTicketsByApprover(User approver) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ticket> query = session.createQuery("from Ticket where approver = :approver", Ticket.class);
        query.setParameter("approver", approver);
        return query.list();
    }

    @Override
    public List<Ticket> getTicketsByAssignee(User assignee) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ticket> query = session.createQuery("from Ticket where assignee = :assignee", Ticket.class);
        query.setParameter("assignee", assignee);
        return query.list();
    }

    @Override
    public List<Ticket> getTicketsByRole(UserRole role) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ticket> query = session.createQuery("from Ticket where owner.role = :role", Ticket.class);
        query.setParameter("role", role);
        return query.list();
    }

    @Override
    public List<Ticket> getTicketsByRoleAndState(UserRole role, State state) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ticket> query = session.createQuery("from Ticket where owner.role = :role and state = :state", Ticket.class);
        query.setParameter("role", role);
        query.setParameter("state", state);
        return query.list();
    }

    @Override
    public List<Ticket> getTicketsByState(State state) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ticket> query = session.createQuery("from Ticket where state = :state", Ticket.class);
        query.setParameter("state", state);
        return query.list();
    }
}
