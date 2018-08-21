package com.epam.service;

import com.epam.entity.Comment;
import java.util.List;

public interface CommentService {

    Comment getCommentById(Long id);

    Comment addComment(Comment comment);

    List<Comment> getCommentsByTicketId(Long id);
}
