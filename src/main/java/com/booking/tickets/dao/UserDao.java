package com.booking.tickets.dao;

import com.booking.tickets.model.User;
import java.util.Optional;

public interface UserDao {
    User add(User user);

    Optional<User> findByEmail(String email);
}
