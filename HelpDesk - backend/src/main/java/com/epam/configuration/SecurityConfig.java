package com.epam.configuration;

import com.epam.enums.UserRole;
import com.epam.security.AuthenticationFilter;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.epam.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.inMemoryAuthentication()
            .passwordEncoder(NoOpPasswordEncoder.getInstance())
            .withUser("user1_mogilev@yopmail.com")
            .password("P@ssword1")
            .roles(UserRole.EMPLOYEE.toString())
            .and()
            .withUser("user2_mogilev@yopmail.com")
            .password("P@ssword1")
            .roles(UserRole.EMPLOYEE.toString())
            .and()
            .withUser("manager1_mogilev@yopmail.com")
            .password("P@ssword1")
            .roles(UserRole.MANAGER.toString())
            .and()
            .withUser("manager2_mogilev@yopmail.com")
            .password("P@ssword1")
            .roles(UserRole.MANAGER.toString())
            .and()
            .withUser("engineer1_mogilev@yopmail.com")
            .password("P@ssword1")
            .roles(UserRole.ENGINEER.toString())
            .and()
            .withUser("engineer2_mogilev@yopmail.com")
            .password("P@ssword1")
            .roles(UserRole.ENGINEER.toString());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/users/*/tickets")
            .hasAnyRole(UserRole.EMPLOYEE.toString(), UserRole.MANAGER.toString())
            .antMatchers(HttpMethod.PUT, "/api/users/*/tickets/*")
            .hasAnyRole(UserRole.EMPLOYEE.toString(), UserRole.MANAGER.toString())
            .antMatchers(HttpMethod.POST, "/api/users/*/tickets/*/feedback")
            .hasAnyRole(UserRole.EMPLOYEE.toString(), UserRole.MANAGER.toString())
            .antMatchers(HttpMethod.PATCH, "/api/users/*/tickets/*")
            .hasAnyRole(UserRole.EMPLOYEE.toString(), UserRole.MANAGER.toString(), UserRole.ENGINEER.toString())
            .antMatchers("api/**")
            .authenticated()
            .and()
            .addFilterBefore(authenticationFilter(authenticationManager()),
                BasicAuthenticationFilter.class)
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .cors()
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .logout().disable()
            .csrf().disable();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(HttpMethod.POST, "/api/login");
    }

    @Bean
    public Filter authenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager);
    }
}
