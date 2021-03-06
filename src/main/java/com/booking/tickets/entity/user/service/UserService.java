package com.booking.tickets.entity.user.service;

import com.booking.tickets.entity.user.model.User;

public interface UserService {
    User add(User user);

    User getByEmail(String email);

    User get(Long id);
}
