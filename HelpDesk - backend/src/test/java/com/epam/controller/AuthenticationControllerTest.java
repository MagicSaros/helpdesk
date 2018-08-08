package com.epam.controller;

import com.epam.dto.AuthenticationTokenDto;
import com.epam.dto.UserDetailsDto;
import com.epam.service.EncryptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private EncryptionService encryptionService;

    @Mock
    private UserDetailsService userDetailsService;

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

        UserDetailsDto userDetailsDto = new UserDetailsDto("username", "password");
        AuthenticationTokenDto tokenDto = new AuthenticationTokenDto(tokenString, tokenHeader);
        UserDetails user = org.mockito.BDDMockito.mock(UserDetails.class);

        org.mockito.BDDMockito.given(userDetailsService.loadUserByUsername(userDetailsDto.getUsername())).willReturn(user);
        org.mockito.BDDMockito.given(user.getPassword()).willReturn("password");
        org.mockito.BDDMockito.given(encryptionService.encode(userDetailsDto.getUsername())).willReturn(tokenString);

        given()
                .contentType("application/json")
                .body(userDetailsDto)
                .when()
                .post(url)
                .then()
                .statusCode(200)
                .and()
                .body("tokenHeader", equalTo(tokenDto.getTokenHeader()))
                .body("tokenString", equalTo(tokenDto.getTokenString()));
    }
}
