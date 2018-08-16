package com.epam.security;

import com.epam.exception.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException)
        throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ApiError apiError = new ApiError.Builder()
            .setTitle("Unauthorized")
            .setStatus(HttpStatus.UNAUTHORIZED.value())
            .setMessage(authException.getMessage())
            .setTimestamp(new Date().getTime())
            .setDeveloperMessage(authException.getClass().getName())
            .build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(apiError);
        response.getOutputStream().println(json);
    }
}
