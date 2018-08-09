package com.epam.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AuthenticationTokenDto {
    private final Long userId;
    private final String tokenString;
    private final String tokenHeader;

    public AuthenticationTokenDto(Long userId, String tokenString, String tokenHeader) {
        this.userId = userId;
        this.tokenString = tokenString;
        this.tokenHeader = tokenHeader;
    }

    public Long getUserId() {
        return userId;
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
            .append("userId", userId)
            .append("tokenString", tokenString)
            .append("tokenHeader", tokenHeader)
            .toString();
    }
}
