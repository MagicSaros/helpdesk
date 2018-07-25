package com.epam.configuration;

import com.epam.handler.AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
public class BeanConfig {
    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new AuthenticationSuccessHandler();
    }
    @Bean
    public SimpleUrlAuthenticationFailureHandler failureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }
}
