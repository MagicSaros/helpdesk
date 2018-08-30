package com.epam.configuration;

import com.epam.enums.UserRole;
import com.epam.security.AuthenticationFilter;
import javax.servlet.Filter;
import javax.sql.DataSource;
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
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception {
        authentication
            .jdbcAuthentication().dataSource(dataSource)
            .passwordEncoder(NoOpPasswordEncoder.getInstance())
            .usersByUsernameQuery("select email, password, enabled from user where email=?")
            .authoritiesByUsernameQuery(
                "select email, user_role from authority inner join user on user.id = authority.user_id where email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String employee = UserRole.EMPLOYEE.toString();
        final String manager = UserRole.MANAGER.toString();
        final String engineer = UserRole.ENGINEER.toString();

        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/users/*/tickets")
            .hasAnyRole(employee, manager)
            .antMatchers(HttpMethod.PUT, "/api/users/*/tickets/*")
            .hasAnyRole(employee, manager)
            .antMatchers(HttpMethod.POST, "/api/users/*/tickets/*/feedback")
            .hasAnyRole(employee, manager)
            .antMatchers(HttpMethod.PATCH, "/api/users/*/tickets/*")
            .hasAnyRole(employee, manager,
                engineer)
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
