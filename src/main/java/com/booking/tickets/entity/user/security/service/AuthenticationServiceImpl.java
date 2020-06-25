package com.booking.tickets.entity.user.security.service;

import com.booking.tickets.entity.role.model.Role;
import com.booking.tickets.entity.role.service.RoleService;
import com.booking.tickets.entity.shoppingcart.service.ShoppingCartService;
import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.service.UserService;
import com.booking.tickets.exception.WrongCredentialsAuthenticationException;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserService userService,
                                     ShoppingCartService shoppingCartService,
                                     RoleService roleService,
                                     PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String email, String password)
            throws WrongCredentialsAuthenticationException {
        User user = userService.getByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongCredentialsAuthenticationException(
                    "The login or password is incorrect");
        }
        return user;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setLogin(email);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = roleService.getByName("USER");
        user.setRoles(Set.of(userRole));
        User userFromDb = userService.add(user);
        shoppingCartService.createShoppingCart(userFromDb);
        return userFromDb;
    }
}
