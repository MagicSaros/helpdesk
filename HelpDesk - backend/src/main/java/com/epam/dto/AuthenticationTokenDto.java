package com.epam.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AuthenticationTokenDto {
    private final UserDto user;
    private final String tokenString;
    private final String tokenHeader;

    public AuthenticationTokenDto(UserDto user, String tokenString, String tokenHeader) {
        this.user = user;
        this.tokenString = tokenString;
        this.tokenHeader = tokenHeader;
    }

    public UserDto getUser() {
        return user;
    }

    public String getTokenString() {
        return tokenString;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("user", user)
            .append("tokenString", tokenString)
            .append("tokenHeader", tokenHeader)
            .toString();
    }
}
