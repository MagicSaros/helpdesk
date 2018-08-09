package com.epam.repository.implementation;

import com.epam.entity.User;
import com.epam.repository.UserRepository;
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
        Query<User> query = session.createQuery("from User as u where u.email = :email", User.class);
        query.setParameter("email", email);
        return query.list().get(0);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        return Optional.ofNullable(user);
    }
}
