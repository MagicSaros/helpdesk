package com.epam.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthenticationTokenProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;

    public AuthenticationTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

        String principal = (String) authenticationToken.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal);
        if (userDetails == null) {
            throw new UsernameNotFoundException("Unknown token");
        }

        authenticationToken.setAuthenticated(true);
        authenticationToken.setDetails(userDetails);

        return authentication;
    }

    @Override
    public boolean supports(Class authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }
}
