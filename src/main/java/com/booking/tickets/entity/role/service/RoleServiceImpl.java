package com.booking.tickets.entity.role.service;

import com.booking.tickets.entity.role.model.Role;
import com.booking.tickets.entity.role.repository.RoleDao;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role add(Role role) {
        return roleDao.add(role);
    }

    @Override
    public Role getByName(String roleName) {
        return roleDao.getByName(roleName);
    }
}
