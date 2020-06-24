package com.booking.tickets.entity.user.service;

import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.repository.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User add(User user) {
        return userDao.add(user);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email).orElse(null);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id);
    }
}
