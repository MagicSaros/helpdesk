package com.epam.repository.implementation;

import com.epam.entity.User;
import com.epam.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session
            .createQuery("from User as u where u.email = :email", User.class);
        query.setParameter("email", email);
        return query.list().get(0);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public User addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user;
    }

    @Override
    public Optional<User> findOne(User user) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User as u"
            + " where u.firstName = :firstName"
            + " and u.lastName = :lastName"
            + " and u.role = :role"
            + " and u.email = :email", User.class);
        query.setParameter("firstName", user.getFirstName());
        query.setParameter("lastName", user.getLastName());
        query.setParameter("role", user.getRole());
        query.setParameter("email", user.getEmail());
        List<User> foundUsers = query.list();
        Optional<User> userOptional = Optional.empty();
        if (foundUsers.size() == 1) {
            userOptional = Optional.of(foundUsers.get(0));
        }
        return userOptional;
    }
}
