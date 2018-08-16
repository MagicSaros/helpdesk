package com.epam.repository.implementation;

import com.epam.entity.Comment;
import com.epam.repository.CommentRepository;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Comment> getCommentById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Comment comment = session.get(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public Comment addComment(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.save(comment);
        return comment;
    }
}
