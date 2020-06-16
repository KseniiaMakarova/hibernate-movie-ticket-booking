package com.booking.tickets.controller;

import com.booking.tickets.entity.role.model.Role;
import com.booking.tickets.entity.role.service.RoleService;
import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class InjectDataController {
    private static final Logger LOGGER = LogManager.getLogger(InjectDataController.class);
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InjectDataController(RoleService roleService, UserService userService,
                                PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        injectRoles();
        injectAdmin();
    }

    private void injectRoles() {
        Role userRole = new Role();
        userRole.setName(Role.RoleName.USER);
        roleService.add(userRole);
        Role adminRole = new Role();
        adminRole.setName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        LOGGER.info("USER and ADMIN roles were injected to DB");
    }

    private void injectAdmin() {
        User admin = new User();
        admin.setLogin("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("1234"));
        Role adminRole = roleService.getRoleByName("ADMIN");
        admin.setRoles(Set.of(adminRole));
        userService.add(admin);
        LOGGER.info("A user with ADMIN role was injected to DB");
    }
}
