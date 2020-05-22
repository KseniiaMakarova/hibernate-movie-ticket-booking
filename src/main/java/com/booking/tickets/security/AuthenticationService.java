package com.booking.tickets.security;

import com.booking.tickets.exception.AuthenticationException;
import com.booking.tickets.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password);
}
