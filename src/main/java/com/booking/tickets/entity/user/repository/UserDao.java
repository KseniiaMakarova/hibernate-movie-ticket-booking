package com.booking.tickets.entity.user.repository;

import com.booking.tickets.dao.GenericDao;
import com.booking.tickets.entity.user.model.User;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByEmail(String email);
}
