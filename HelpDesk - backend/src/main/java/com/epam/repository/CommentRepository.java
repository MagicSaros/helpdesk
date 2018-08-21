package com.epam.repository;

import com.epam.entity.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> getCommentById(Long id);

    Comment addComment(Comment comment);

    List<Comment> getCommentsByTicketId(Long id);
}
