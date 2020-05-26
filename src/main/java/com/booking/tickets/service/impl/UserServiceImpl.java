package com.booking.tickets.service.impl;

import com.booking.tickets.dao.UserDao;
import com.booking.tickets.lib.Inject;
import com.booking.tickets.lib.Service;
import com.booking.tickets.model.User;
import com.booking.tickets.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        return userDao.add(user);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElse(null);
    }
}
