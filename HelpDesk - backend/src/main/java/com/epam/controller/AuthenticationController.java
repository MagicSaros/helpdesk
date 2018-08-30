package com.epam.controller;

import com.epam.converter.implementation.UserDtoConverter;
import com.epam.dto.AuthenticationTokenDto;
import com.epam.dto.UserDetailsDto;
import com.epam.entity.User;
import com.epam.exception.BadCredentialsException;
import com.epam.service.EncryptionService;
import com.epam.service.TokenService;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthenticationController {

    private static final String TOKEN_HEADER = "Auth-Token";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationTokenDto> login(
        @RequestBody final UserDetailsDto userDetailsDto) {
        String username = userDetailsDto.getUsername();

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid login or password");
        }

        boolean isMatch = userDetailsDto.getPassword().equals(userDetails.getPassword());

        if (isMatch) {
            User user = userService.getUserByEmail(username);
            String accessToken = tokenService.generateToken(user);
            AuthenticationTokenDto token = new AuthenticationTokenDto(
                userDtoConverter.fromEntityToDto(user), accessToken, TOKEN_HEADER);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            throw new BadCredentialsException("Invalid login or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((org.springframework.security.core.userdetails.User) authentication
            .getPrincipal()).getUsername();
        User user = userService.getUserByEmail(username);
        tokenService.clearToken(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
