package com.epam.converter.implementation;

import com.epam.converter.DtoConverter;
import com.epam.dto.TicketDto;
import com.epam.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketDtoConverter implements DtoConverter<Ticket, TicketDto> {

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Autowired
    private CategoryDtoConverter categoryDtoConverter;

    @Override
    public TicketDto fromEntityToDto(final Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        return new TicketDto.Builder()
            .setId(ticket.getId())
            .setName(ticket.getName())
            .setDescription(ticket.getDescription())
            .setCreatedOn(ticket.getCreatedOn())
            .setDesiredResolutionDate(ticket.getDesiredResolutionDate())
            .setAssignee(userDtoConverter.fromEntityToDto(ticket.getAssignee()))
            .setOwner(userDtoConverter.fromEntityToDto(ticket.getOwner()))
            .setState(ticket.getState())
            .setCategory(categoryDtoConverter.fromEntityToDto(ticket.getCategory()))
            .setUrgency(ticket.getUrgency())
            .setApprover(userDtoConverter.fromEntityToDto(ticket.getApprover()))
            .build();
    }

    @Override
    public Ticket fromDtoToEntity(final TicketDto dto) {
        if (dto == null) {
            return null;
        }
        return new Ticket.Builder()
            .setId(dto.getId())
            .setName(dto.getName())
            .setDescription(dto.getDescription())
            .setCreatedOn(dto.getCreatedOn())
            .setDesiredResolutionDate(dto.getDesiredResolutionDate())
            .setAssignee(userDtoConverter.fromDtoToEntity(dto.getAssignee()))
            .setOwner(userDtoConverter.fromDtoToEntity(dto.getOwner()))
            .setState(dto.getState())
            .setCategory(categoryDtoConverter.fromDtoToEntity(dto.getCategory()))
            .setUrgency(dto.getUrgency())
            .setApprover(userDtoConverter.fromDtoToEntity(dto.getApprover()))
            .build();
    }
}
