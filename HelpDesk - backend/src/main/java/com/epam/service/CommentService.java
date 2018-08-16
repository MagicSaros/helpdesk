package com.epam.service;

import com.epam.entity.Comment;

public interface CommentService {

    Comment getCommentById(Long id);

    Comment addComment(Comment comment);
}
