package com.epam.converter;

import com.epam.converter.implementation.CategoryDtoConverter;
import com.epam.converter.implementation.TicketDtoConverter;
import com.epam.converter.implementation.UserDtoConverter;
import com.epam.dto.CategoryDto;
import com.epam.dto.TicketDto;
import com.epam.dto.UserDto;
import com.epam.entity.Category;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.enums.State;
import com.epam.enums.Urgency;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.BDDMockito.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(MockitoJUnitRunner.class)
public class TicketDtoConverterTest {

    private Ticket ticket;

    private TicketDto ticketDto;

    @Mock
    private UserDtoConverter userDtoConverter;

    @Mock
    private CategoryDtoConverter categoryDtoConverter;

    @InjectMocks
    private TicketDtoConverter ticketDtoConverter = new TicketDtoConverter();

    @Before
    public void init() {
        User user = new User();
        Category category = new Category();

        UserDto userDto = new UserDto();
        CategoryDto categoryDto = new CategoryDto();

        Date date = new GregorianCalendar(2018, 8, 8).getTime();

        ticket = new Ticket.Builder()
            .setId((long) 1)
            .setName("Name")
            .setDescription("Category")
            .setCreatedOn(date)
            .setDesiredResolutionDate(date)
            .setAssignee(user)
            .setOwner(user)
            .setCategory(category)
            .setState(State.NEW)
            .setUrgency(Urgency.MEDIUM)
            .setApprover(user)
            .build();

        ticketDto = new TicketDto.Builder()
            .setId((long) 1)
            .setName("Name")
            .setDescription("Category")
            .setCreatedOn(date)
            .setDesiredResolutionDate(date)
            .setAssignee(userDto)
            .setOwner(userDto)
            .setCategory(categoryDto)
            .setState(State.NEW)
            .setUrgency(Urgency.MEDIUM)
            .setApprover(userDto)
            .build();
    }

    @Test
    public void fromEntityToDtoTest() {
        given(userDtoConverter.fromEntityToDto(ticket.getApprover())).willReturn(ticketDto.getApprover());
        given(categoryDtoConverter.fromEntityToDto(ticket.getCategory())).willReturn(ticketDto.getCategory());

        TicketDto actual = ticketDtoConverter.fromEntityToDto(ticket);

        assertEquals(ticketDto.getId(), actual.getId());
        assertEquals(ticketDto.getName(), actual.getName());
        assertEquals(ticketDto.getDescription(), actual.getDescription());
        assertEquals(ticketDto.getCreatedOn(), actual.getCreatedOn());
        assertEquals(ticketDto.getDesiredResolutionDate(), actual.getDesiredResolutionDate());
        assertEquals(ticketDto.getAssignee(), actual.getAssignee());
        assertEquals(ticketDto.getOwner(), actual.getOwner());
        assertEquals(ticketDto.getCategory(), actual.getCategory());
        assertEquals(ticketDto.getUrgency(), actual.getUrgency());
        assertEquals(ticketDto.getState(), actual.getState());
        assertEquals(ticketDto.getApprover(), actual.getApprover());
    }

    @Test
    public void fromDtoToEntityTest() {
        given(userDtoConverter.fromDtoToEntity(ticketDto.getApprover())).willReturn(ticket.getApprover());
        given(categoryDtoConverter.fromDtoToEntity(ticketDto.getCategory())).willReturn(ticket.getCategory());

        Ticket actual = ticketDtoConverter.fromDtoToEntity(ticketDto);

        assertEquals(ticket.getId(), actual.getId());
        assertEquals(ticket.getName(), actual.getName());
        assertEquals(ticket.getDescription(), actual.getDescription());
        assertEquals(ticket.getCreatedOn(), actual.getCreatedOn());
        assertEquals(ticket.getDesiredResolutionDate(), actual.getDesiredResolutionDate());
        assertEquals(ticket.getAssignee(), actual.getAssignee());
        assertEquals(ticket.getOwner(), actual.getOwner());
        assertEquals(ticket.getCategory(), actual.getCategory());
        assertEquals(ticket.getUrgency(), actual.getUrgency());
        assertEquals(ticket.getState(), actual.getState());
        assertEquals(ticket.getApprover(), actual.getApprover());
    }
}
