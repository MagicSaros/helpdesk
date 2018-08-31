package com.epam.service;

import com.epam.entity.Comment;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import java.util.List;

public interface CommentService {

    Comment getCommentById(Long id);

    Comment addComment(Ticket ticket, User user, Comment comment);

    List<Comment> getCommentsByTicketId(Long id);
}
