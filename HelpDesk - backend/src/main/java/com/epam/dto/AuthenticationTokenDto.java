package com.epam.dto;

public class AuthenticationTokenDto {
    private final String tokenString;
    private final String tokenHeader;

    public AuthenticationTokenDto(String tokenString, String tokenHeader) {
        this.tokenString = tokenString;
        this.tokenHeader = tokenHeader;
    }

    public String getTokenString() {
        return tokenString;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    @Override
    public String toString() {
        return tokenHeader + " : " + tokenString;
    }
}
