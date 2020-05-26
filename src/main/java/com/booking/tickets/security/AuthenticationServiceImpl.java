package com.booking.tickets.security;

import com.booking.tickets.exception.AuthenticationException;
import com.booking.tickets.lib.Inject;
import com.booking.tickets.lib.Service;
import com.booking.tickets.model.User;
import com.booking.tickets.service.UserService;
import com.booking.tickets.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email);
        if (user == null || !HashUtil.hashPassword(password, user.getSalt())
                .equals(user.getPassword())) {
            throw new AuthenticationException("The login or password is incorrect");
        }
        return user;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setLogin(email);
        byte[] salt = HashUtil.getSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(password, salt));
        return userService.add(user);
    }
}
