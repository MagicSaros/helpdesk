package com.epam.service;

import com.epam.entity.Token;
import com.epam.entity.User;
import java.util.Optional;

public interface TokenService {

    String generateToken(User user);

    boolean isAccessTokenValid(Token token);

    void clearToken(User user);

    Optional<String> getUsernameFromAccessToken(String accessToken);
}
