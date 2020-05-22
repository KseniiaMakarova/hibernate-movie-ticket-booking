package com.booking.tickets.dao;

import com.booking.tickets.model.User;

public interface UserDao {
    User add(User user);

    User findByEmail(String email);
}
