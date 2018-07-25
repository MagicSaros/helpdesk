package com.epam.configuration;

import com.epam.enums.UserRole;
import com.epam.handler.AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("com.epam.component")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.inMemoryAuthentication()
                .withUser("user1_mogilev@yopmail.com").password("{noop}P@ssword1")
                .roles(UserRole.EMPLOYEE.toString())
                .and()
                .withUser("user2_mogilev@yopmail.com").password("{noop}P@ssword1")
                .roles(UserRole.EMPLOYEE.toString())
                .and()
                .withUser("manager1_mogilev@yopmail.com").password("{noop}P@ssword1")
                .roles(UserRole.MANAGER.toString())
                .and()
                .withUser("manager2_mogilev@yopmail.com").password("{noop}P@ssword1")
                .roles(UserRole.MANAGER.toString())
                .and()
                .withUser("engineer1_mogilev@yopmail.com").password("{noop}P@ssword1")
                .roles(UserRole.ENGINEER.toString())
                .and()
                .withUser("engineer2_mogilev@yopmail.com").password("{noop}P@ssword1")
                .roles(UserRole.ENGINEER.toString());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/api/**").authenticated()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
