package com.epam.repository;

import com.epam.entity.Token;
import java.util.Optional;

public interface TokenRepository {

    Token addToken(Token token);

    Optional<Token> getTokenByUserId(Long id);

    void removeToken(Token token);
}
