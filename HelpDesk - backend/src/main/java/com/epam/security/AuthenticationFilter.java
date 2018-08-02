package com.epam.security;

import com.epam.exception.AuthenticationTokenHeaderNotFoundException;
import com.epam.exception.InvalidAuthenticationTokenException;
import com.epam.service.EncryptionService;
import org.apache.log4j.Logger;
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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationFilter extends GenericFilterBean {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);
    private static final String HEADER = "Auth-Token";

    private final AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private EncryptionService encryptionService;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        try {
            String tokenString = Optional
                    .ofNullable(httpServletRequest.getHeader(HEADER))
                    .orElseThrow(() -> new AuthenticationTokenHeaderNotFoundException("Header " + HEADER + " not found"));
            LOGGER.debug("Token string: " + tokenString);

            String username = encryptionService.decode(tokenString);
            LOGGER.debug("Decoded username: " + username);

            UserDetails user = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            LOGGER.debug("Authentication token: " + authenticationToken.toString());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            LOGGER.debug("Is authenticated: " + authentication.isAuthenticated());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (UsernameNotFoundException e) {
            LOGGER.error("Username not found: " + e.getMessage());
            authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, new InvalidAuthenticationTokenException("Invalid token"));
        } catch (AuthenticationException e) {
            LOGGER.error(e.getMessage());
            authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, e);
        }
    }
}
