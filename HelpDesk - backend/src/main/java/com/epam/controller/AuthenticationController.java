package com.epam.controller;

import com.epam.dto.AuthenticationTokenDto;
import com.epam.dto.UserDetailsDto;
import com.epam.entity.User;
import com.epam.exception.BadCredentialsException;
import com.epam.service.EncryptionService;
import com.epam.service.UserService;
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
    private UserService userService;

    @Autowired
    private EncryptionService encryptionService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationTokenDto> login(
        @RequestBody final UserDetailsDto userDetailsDto) {
        LOGGER.debug("Got username: " + userDetailsDto.getUsername());

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(userDetailsDto.getUsername());
        } catch (UsernameNotFoundException e) {
            LOGGER.info("Not verified");
            throw new BadCredentialsException("Invalid login or password");
        }

        if (userDetails.getPassword().equals(userDetailsDto.getPassword())) {
            LOGGER.info("Verified");
            User user = userService.getUserByEmail(userDetails.getUsername());
            String tokenString = encryptionService.encode(userDetailsDto.getUsername());
            AuthenticationTokenDto token = new AuthenticationTokenDto(user.getId(), tokenString, TOKEN_HEADER);
            LOGGER.debug("Token: " + token.toString());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            LOGGER.info("Not verified");
            throw new BadCredentialsException("Invalid login or password");
        }
    }
}
