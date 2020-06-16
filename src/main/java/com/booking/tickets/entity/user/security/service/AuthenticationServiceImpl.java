package com.booking.tickets.entity.user.security.service;

import com.booking.tickets.entity.role.model.Role;
import com.booking.tickets.entity.role.service.RoleService;
import com.booking.tickets.entity.shoppingcart.service.ShoppingCartService;
import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.service.UserService;
import com.booking.tickets.exception.AuthenticationException;
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
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email);
        if (user == null || !passwordEncoder.encode(password).equals(user.getPassword())) {
            throw new AuthenticationException("The login or password is incorrect");
        }
        return user;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setLogin(email);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = roleService.getRoleByName("USER");
        user.setRoles(Set.of(userRole));
        User userFromDb = userService.add(user);
        shoppingCartService.registerNewShoppingCart(userFromDb);
        return userFromDb;
    }
}
