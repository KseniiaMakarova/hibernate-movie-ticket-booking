package com.booking.tickets.util;

import com.booking.tickets.entity.role.model.Role;
import com.booking.tickets.entity.role.service.RoleService;
import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InjectDataAssistant {
    private static final Logger LOGGER = LogManager.getLogger(InjectDataAssistant.class);
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InjectDataAssistant(RoleService roleService, UserService userService,
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
        LOGGER.log(Level.INFO, "USER and ADMIN roles were injected to DB");
    }

    private void injectAdmin() {
        User admin = new User();
        admin.setLogin("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("1234"));
        Role adminRole = roleService.getByName("ADMIN");
        admin.setRoles(Set.of(adminRole));
        userService.add(admin);
        LOGGER.log(Level.INFO, "A user with ADMIN role was injected to DB");
    }
}
