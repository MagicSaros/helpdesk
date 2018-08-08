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
    public TicketDto fromEntityToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setName(ticket.getName());
        ticketDto.setDescription(ticket.getDescription());
        ticketDto.setCreatedOn(ticket.getCreatedOn());
        ticketDto.setDesiredResolutionDate(ticket.getDesiredResolutionDate());
        ticketDto.setAssignee(userDtoConverter.fromEntityToDto(ticket.getAssignee()));
        ticketDto.setOwner(userDtoConverter.fromEntityToDto(ticket.getOwner()));
        ticketDto.setState(ticket.getState());
        ticketDto.setCategory(categoryDtoConverter.fromEntityToDto(ticket.getCategory()));
        ticketDto.setUrgency(ticket.getUrgency());
        ticketDto.setApprover(userDtoConverter.fromEntityToDto(ticket.getApprover()));
        return ticketDto;
    }

    @Override
    public Ticket fromDtoToEntity(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketDto.getId());
        ticket.setName(ticketDto.getName());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setCreatedOn(ticketDto.getCreatedOn());
        ticket.setDesiredResolutionDate(ticketDto.getDesiredResolutionDate());
        ticket.setAssignee(userDtoConverter.fromDtoToEntity(ticketDto.getAssignee()));
        ticket.setOwner(userDtoConverter.fromDtoToEntity(ticketDto.getOwner()));
        ticket.setState(ticketDto.getState());
        ticket.setCategory(categoryDtoConverter.fromDtoToEntity(ticketDto.getCategory()));
        ticket.setUrgency(ticketDto.getUrgency());
        ticket.setApprover(userDtoConverter.fromDtoToEntity(ticketDto.getApprover()));
        return ticket;
    }
}
