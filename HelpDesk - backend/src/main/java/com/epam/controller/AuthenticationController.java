package com.epam.controller;

import com.epam.converter.implementation.UserDtoConverter;
import com.epam.dto.AuthenticationTokenDto;
import com.epam.dto.UserDetailsDto;
import com.epam.entity.User;
import com.epam.exception.ApiError;
import com.epam.exception.BadCredentialsException;
import com.epam.service.TokenService;
import com.epam.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
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
@Api(value = "authentication", description = "Authentication API")
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
    @ApiOperation(value = "Login to application", response = AuthenticationTokenDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = AuthenticationTokenDto.class),
        @ApiResponse(code = 401, message = "Fail", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
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
    @ApiOperation(value = "Logout from application")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 404, message = "Fail", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((org.springframework.security.core.userdetails.User) authentication
            .getPrincipal()).getUsername();
        User user = userService.getUserByEmail(username);
        tokenService.clearToken(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
