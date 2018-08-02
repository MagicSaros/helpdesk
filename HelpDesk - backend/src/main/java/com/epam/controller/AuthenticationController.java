package com.epam.controller;

import com.epam.dto.AuthenticationTokenDto;
import com.epam.dto.UserDto;
import com.epam.exception.BadCredentialsException;
import com.epam.service.EncryptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthenticationController {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class);
    private static final String TOKEN_HEADER = "Auth-Token";

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private EncryptionService encryptionService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationTokenDto> login(@RequestBody UserDto userDto) {
        LOGGER.debug("Got username: " + userDto.getUsername());

        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(userDto.getUsername());
        } catch (UsernameNotFoundException e) {
            LOGGER.info("Not verified");
            throw new BadCredentialsException("Invalid login or password");
        }

        if (user.getPassword().equals(userDto.getPassword())) {
            LOGGER.info("Verified");
            String tokenString = encryptionService.encode(userDto.getUsername());
            AuthenticationTokenDto token = new AuthenticationTokenDto(tokenString, TOKEN_HEADER);
            LOGGER.debug("Token: " + token.toString());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            LOGGER.info("Not verified");
            throw new BadCredentialsException("Invalid login or password");
        }
    }
}
