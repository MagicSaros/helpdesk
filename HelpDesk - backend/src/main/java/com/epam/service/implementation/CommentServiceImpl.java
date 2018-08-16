package com.epam.service.implementation;

import com.epam.entity.Comment;
import com.epam.exception.CommentNotFoundException;
import com.epam.repository.CommentRepository;
import com.epam.service.CommentService;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Override
    public Comment getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.getCommentById(id);
        return comment
            .orElseThrow(() -> new CommentNotFoundException("Comment not found by passed id"));
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setDate(new Date());
        return commentRepository.addComment(comment);
    }
}
