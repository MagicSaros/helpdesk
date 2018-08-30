package com.epam.repository.implementation;

import com.epam.entity.Token;
import com.epam.repository.TokenRepository;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Token addToken(Token token) {
        Session session = sessionFactory.getCurrentSession();
        session.save(token);
        return token;
    }

    @Override
    public Optional<Token> getTokenByUserId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Token> query = session.createQuery("from Token where userId = :id", Token.class);
        query.setParameter("id", id);
        return query.uniqueResultOptional();
    }

    @Override
    public void removeToken(Token token) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(token);
    }
}
