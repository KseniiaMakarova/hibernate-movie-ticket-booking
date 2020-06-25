package com.booking.tickets.config;

import com.booking.tickets.entity.user.security.jwt.JwtConfigurer;
import com.booking.tickets.entity.user.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/register", "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/movies/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, "/cinema-halls/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, "/movie-sessions/**").hasRole(ADMIN)
                .antMatchers("/users/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/movies/**").hasAnyRole(USER, ADMIN)
                .antMatchers(HttpMethod.GET, "/cinema-halls/**").hasAnyRole(USER, ADMIN)
                .antMatchers(HttpMethod.GET, "/movie-sessions/**").hasAnyRole(USER, ADMIN)
                .antMatchers("/shopping-carts/**", "/orders/**").hasRole(USER)
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
