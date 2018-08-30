package com.epam.security;

import com.epam.entity.Token;
import com.epam.entity.User;
import com.epam.exception.AuthenticationTokenHeaderNotFoundException;
import com.epam.exception.InvalidAuthenticationTokenException;
import com.epam.service.EncryptionService;
import com.epam.service.TokenService;
import com.epam.service.UserService;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean {

    private static final String HEADER = "Auth-Token";

    private final AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private TokenService tokenService;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        try {
            String accessToken = Optional
                .ofNullable(httpServletRequest.getHeader(HEADER))
                .orElseThrow(() -> new AuthenticationTokenHeaderNotFoundException(
                    "Header " + HEADER + " not found"));

            String username = tokenService
                .getUsernameFromAccessToken(accessToken)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

            User user = userService.getUserByEmail(username);
            boolean isValid = tokenService.isAccessTokenValid(new Token(user.getId(), accessToken));

            if (!isValid) {
                throw new InvalidAuthenticationTokenException("Invalid token");
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
            authenticationToken.setDetails(userDetails);

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (UsernameNotFoundException e) {
            authenticationEntryPoint.commence(httpServletRequest, httpServletResponse,
                new InvalidAuthenticationTokenException("Invalid token"));
        } catch (AuthenticationException e) {
            authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, e);
        }
    }
}
