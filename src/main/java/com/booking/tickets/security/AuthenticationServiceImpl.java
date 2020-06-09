package com.booking.tickets.security;

import com.booking.tickets.exception.AuthenticationException;
import com.booking.tickets.model.User;
import com.booking.tickets.service.ShoppingCartService;
import com.booking.tickets.service.UserService;
import com.booking.tickets.util.HashUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(
            UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

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
        User userFromDb = userService.add(user);
        shoppingCartService.registerNewShoppingCart(userFromDb);
        return userFromDb;
    }
}
