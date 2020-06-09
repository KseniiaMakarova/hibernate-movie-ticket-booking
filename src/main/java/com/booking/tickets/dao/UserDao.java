package com.booking.tickets.dao;

import com.booking.tickets.model.User;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByEmail(String email);
}
