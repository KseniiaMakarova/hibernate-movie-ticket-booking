package com.booking.tickets.entity.role.service;

import com.booking.tickets.entity.role.model.Role;

public interface RoleService {
    Role add(Role role);

    Role getByName(String roleName);
}
