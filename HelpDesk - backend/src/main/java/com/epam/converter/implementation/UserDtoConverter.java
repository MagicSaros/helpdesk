package com.epam.converter.implementation;

import com.epam.converter.DtoConverter;
import com.epam.dto.UserDto;
import com.epam.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements DtoConverter<User, UserDto> {

    @Override
    public UserDto fromEntityToDto(final User user) {
        if (user == null) {
            return null;
        }
        return new UserDto.Builder()
            .setId(user.getId())
            .setFirstName(user.getFirstName())
            .setLastName(user.getLastName())
            .setRole(user.getRole())
            .setEmail(user.getEmail())
            .setPassword(user.getPassword())
            .build();
    }

    @Override
    public User fromDtoToEntity(final UserDto dto) {
        if (dto == null) {
            return null;
        }
        return new User.Builder()
            .setId(dto.getId())
            .setFirstName(dto.getFirstName())
            .setLastName(dto.getLastName())
            .setRole(dto.getRole())
            .setEmail(dto.getEmail())
            .setPassword(dto.getPassword())
            .build();
    }
}
