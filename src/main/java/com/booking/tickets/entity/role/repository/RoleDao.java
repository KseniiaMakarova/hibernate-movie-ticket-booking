package com.booking.tickets.entity.role.repository;

import com.booking.tickets.dao.GenericDao;
import com.booking.tickets.entity.role.model.Role;

public interface RoleDao extends GenericDao<Role> {
    Role getByName(String roleName);
}
