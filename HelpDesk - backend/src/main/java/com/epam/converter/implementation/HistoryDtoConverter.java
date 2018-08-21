package com.epam.converter.implementation;

import com.epam.converter.DtoConverter;
import com.epam.dto.HistoryDto;
import com.epam.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoryDtoConverter implements DtoConverter<History, HistoryDto> {

    @Autowired
    private TicketDtoConverter ticketDtoConverter;

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Override
    public HistoryDto fromEntityToDto(final History history) {
        if (history == null) {
            return null;
        }
        return new HistoryDto.Builder()
            .setId(history.getId())
            .setTicket(ticketDtoConverter.fromEntityToDto(history.getTicket()))
            .setDate(history.getDate())
            .setAction(history.getAction())
            .setUser(userDtoConverter.fromEntityToDto(history.getUser()))
            .setDescription(history.getDescription())
            .build();
    }

    @Override
    public History fromDtoToEntity(final HistoryDto dto) {
        if (dto == null) {
            return null;
        }
        return new History.Builder()
            .setId(dto.getId())
            .setTicket(ticketDtoConverter.fromDtoToEntity(dto.getTicket()))
            .setDate(dto.getDate())
            .setAction(dto.getAction())
            .setUser(userDtoConverter.fromDtoToEntity(dto.getUser()))
            .setDescription(dto.getDescription())
            .build();
    }
}
