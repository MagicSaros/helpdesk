package com.epam.converter.implementation;

import com.epam.converter.DtoConverter;
import com.epam.dto.CommentDto;
import com.epam.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoConverter implements DtoConverter<Comment, CommentDto> {

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Autowired
    private TicketDtoConverter ticketDtoConverter;

    @Override
    public CommentDto fromEntityToDto(final Comment comment) {
        if (comment == null) {
            return null;
        }
        return new CommentDto.Builder()
            .setId(comment.getId())
            .setUser(userDtoConverter.fromEntityToDto(comment.getUser()))
            .setText(comment.getText())
            .setDate(comment.getDate())
            .setTicket(ticketDtoConverter.fromEntityToDto(comment.getTicket()))
            .build();
    }

    @Override
    public Comment fromDtoToEntity(final CommentDto dto) {
        if (dto == null) {
            return null;
        }
        return new Comment.Builder()
            .setId(dto.getId())
            .setUser(userDtoConverter.fromDtoToEntity(dto.getUser()))
            .setText(dto.getText())
            .setDate(dto.getDate())
            .setTicket(ticketDtoConverter.fromDtoToEntity(dto.getTicket()))
            .build();
    }
}
