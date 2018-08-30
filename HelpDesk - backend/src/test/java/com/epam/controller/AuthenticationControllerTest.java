package com.epam.controller;

import com.epam.converter.implementation.UserDtoConverter;
import com.epam.dto.AuthenticationTokenDto;
import com.epam.dto.UserDetailsDto;
import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.service.TokenService;
import com.epam.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    private static final String URL_PREFIX = "/api";

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserService userService;

    @Mock
    private UserDtoConverter userDtoConverter;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationController authenticationController = new AuthenticationController();

    @Before
    public void init() {
        standaloneSetup(authenticationController);
    }

    @Test
    public void loginTest() {
        String url = URL_PREFIX + "/login";
        String tokenString = "dXNlcm5hbWU=";
        String tokenHeader = "Auth-Token";
        User user = new User.Builder()
            .setEmail("email")
            .setPassword(("password"))
            .build();

        UserDto userDto = new UserDto.Builder()
            .setEmail("email")
            .setPassword("password")
            .build();

        UserDetailsDto userDetailsDto = new UserDetailsDto("email", "password");
        AuthenticationTokenDto tokenDto = new AuthenticationTokenDto(userDto, tokenString,
            tokenHeader);
        UserDetails userDetails = BDDMockito.mock(UserDetails.class);

        BDDMockito.given(userDetailsService.loadUserByUsername(userDetailsDto.getUsername()))
            .willReturn(userDetails);
        BDDMockito.given(userDetails.getPassword()).willReturn("password");
        BDDMockito.given(userService.getUserByEmail(userDetailsDto.getUsername())).willReturn(user);
        BDDMockito.given(userDtoConverter.fromEntityToDto(user)).willReturn(userDto);
        BDDMockito.given(tokenService.generateToken(user)).willReturn(tokenString);

        given()
            .contentType("application/json")
            .body(userDetailsDto)
            .when()
            .post(url)
            .then()
            .statusCode(200)
            .and()
            .body("user.email", equalTo(user.getEmail()))
            .body("tokenHeader", equalTo(tokenDto.getTokenHeader()))
            .body("tokenString", equalTo(tokenDto.getTokenString()));
    }
}
