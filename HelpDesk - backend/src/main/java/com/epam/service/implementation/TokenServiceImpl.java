package com.epam.service.implementation;

import com.epam.component.RandomHashGenerator;
import com.epam.entity.Token;
import com.epam.entity.User;
import com.epam.repository.TokenRepository;
import com.epam.service.EncryptionService;
import com.epam.service.TokenService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private RandomHashGenerator hashGenerator;

    @Override
    public String generateToken(User user) {
        String username = user.getEmail();
        String hash = hashGenerator.generate(username);
        String accessToken = encryptionService.encode(username + ":" + hash);

        tokenRepository.addToken(new Token(user.getId(), accessToken));
        return accessToken;
    }

    @Override
    public boolean isAccessTokenValid(Token token) {
        Long userId = token.getUserId();
        String accessToken = token.getAccessToken();
        Token existentToken = tokenRepository.getTokenByUserId(userId).orElse(new Token());
        return accessToken.equals(existentToken.getAccessToken());
    }

    @Override
    public void clearToken(User user) {
        Optional<Token> optionalToken = tokenRepository.getTokenByUserId(user.getId());
        optionalToken.ifPresent(token -> tokenRepository.removeToken(token));
    }

    @Override
    public Optional<String> getUsernameFromAccessToken(String accessToken) {
        String decodedToken = encryptionService.decode(accessToken);
        if (decodedToken.contains(":")) {
            return Optional.of(decodedToken.split(":")[0]);
        }
        return Optional.empty();
    }
}
