package com.booking.tickets.service;

import com.booking.tickets.model.User;

public interface UserService {
    User add(User user);

    User findByEmail(String email);

    User get(Long id);
}
