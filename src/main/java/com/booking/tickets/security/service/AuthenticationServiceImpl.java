package com.booking.tickets.security.service;

import com.booking.tickets.entity.shoppingcart.service.ShoppingCartService;
import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.service.UserService;
import com.booking.tickets.exception.AuthenticationException;
import com.booking.tickets.util.HashUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final HashUtil hashUtil;

    public AuthenticationServiceImpl(
            UserService userService, ShoppingCartService shoppingCartService, HashUtil hashUtil) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.hashUtil = hashUtil;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email);
        if (user == null || !hashUtil.hashPassword(password, user.getSalt())
                .equals(user.getPassword())) {
            throw new AuthenticationException("The login or password is incorrect");
        }
        return user;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setLogin(email);
        byte[] salt = hashUtil.getSalt();
        user.setSalt(salt);
        user.setPassword(hashUtil.hashPassword(password, salt));
        User userFromDb = userService.add(user);
        shoppingCartService.registerNewShoppingCart(userFromDb);
        return userFromDb;
    }
}
