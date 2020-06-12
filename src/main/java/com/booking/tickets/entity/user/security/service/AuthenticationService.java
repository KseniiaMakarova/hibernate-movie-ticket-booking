package com.booking.tickets.entity.user.security.service;

import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.exception.AuthenticationException;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password);
}
