package com.epam.converter;

import com.epam.converter.implementation.UserDtoConverter;
import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.enums.UserRole;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserDtoConverterTest {

    private User user;

    private UserDto userDto;

    private UserDtoConverter userDtoConverter;

    @Before
    public void init() {
        userDtoConverter = new UserDtoConverter();

        user = new User();
        user.setFirstName("First");
        user.setLastName("Last");
        user.setRole(UserRole.MANAGER);
        user.setEmail("email");
        user.setPassword("password");

        userDto = new UserDto();
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setRole(UserRole.MANAGER);
        userDto.setEmail("email");
        userDto.setPassword("password");
    }

    @Test
    public void fromEntityToDtoTest() {
        UserDto actual = userDtoConverter.fromEntityToDto(user);

        assertEquals(userDto.getFirstName(), actual.getFirstName());
        assertEquals(userDto.getLastName(), actual.getLastName());
        assertEquals(userDto.getRole(), actual.getRole());
        assertEquals(userDto.getEmail(), actual.getEmail());
        assertEquals(userDto.getPassword(), actual.getPassword());
    }

    @Test
    public void fromDtoToEntityTest() {
        User actual = userDtoConverter.fromDtoToEntity(userDto);

        assertEquals(user.getFirstName(), actual.getFirstName());
        assertEquals(user.getLastName(), actual.getLastName());
        assertEquals(user.getRole(), actual.getRole());
        assertEquals(user.getEmail(), actual.getEmail());
        assertEquals(user.getPassword(), actual.getPassword());
    }
}
