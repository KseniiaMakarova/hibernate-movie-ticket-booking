package com.booking.tickets.entity.user.security.service;

import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByEmail(username);
        UserBuilder userBuilder;
        if (user != null) {
            userBuilder
                    = org.springframework.security.core.userdetails.User.withUsername(username);
            userBuilder.password(user.getPassword());
            userBuilder.roles(user.getRoles()
                    .stream()
                    .map(role -> role.getName().name())
                    .toArray(String[]::new));
        } else {
            throw new UsernameNotFoundException("User was not found");
        }
        return userBuilder.build();
    }
}
