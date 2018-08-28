package com.epam.converter.implementation;

import com.epam.converter.DtoConverter;
import com.epam.dto.FeedbackDto;
import com.epam.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedbackDtoConverter implements DtoConverter<Feedback, FeedbackDto> {

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Autowired
    private TicketDtoConverter ticketDtoConverter;

    @Override
    public FeedbackDto fromEntityToDto(final Feedback feedback) {
        if (feedback == null) {
            return null;
        }
        return new FeedbackDto.Builder()
            .setId(feedback.getId())
            .setUser(userDtoConverter.fromEntityToDto(feedback.getUser()))
            .setRate(feedback.getRate())
            .setDate(feedback.getDate())
            .setText(feedback.getText())
            .setTicket(ticketDtoConverter.fromEntityToDto(feedback.getTicket()))
            .build();
    }

    @Override
    public Feedback fromDtoToEntity(final FeedbackDto dto) {
        if (dto == null) {
            return null;
        }
        return new Feedback.Builder()
            .setId(dto.getId())
            .setUser(userDtoConverter.fromDtoToEntity(dto.getUser()))
            .setRate(dto.getRate())
            .setDate(dto.getDate())
            .setText(dto.getText())
            .setTicket(ticketDtoConverter.fromDtoToEntity(dto.getTicket()))
            .build();
    }
}
