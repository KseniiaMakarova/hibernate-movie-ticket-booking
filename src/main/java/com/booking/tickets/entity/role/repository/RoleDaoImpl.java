package com.booking.tickets.entity.role.repository;

import com.booking.tickets.dao.GenericDaoImpl;
import com.booking.tickets.entity.role.model.Role;
import com.booking.tickets.exception.DataProcessingException;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {
    private static final Logger LOGGER = LogManager.getLogger(RoleDaoImpl.class);
    private final SessionFactory sessionFactory;

    public RoleDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role add(Role element) {
        Role role = super.add(element);
        LOGGER.log(Level.INFO, "{} was inserted to DB", element);
        return role;
    }

    @Override
    public List<Role> getAll() {
        return super.getAll(Role.class);
    }

    @Override
    public Role get(Long id) {
        return super.get(Role.class, id);
    }

    @Override
    public Role getRoleByName(String roleName) {
        try (Session session = sessionFactory.openSession()) {
            Role.RoleName roleNameObject = Role.RoleName.valueOf(roleName);
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Role> query
                    = criteriaBuilder.createQuery(Role.class);
            Root<Role> root = query.from(Role.class);
            return session.createQuery(
                    query.where(criteriaBuilder.equal(root.get("name"), roleNameObject)))
                    .getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "There was an error retrieving a role with name " + roleName, e);
        }
    }
}
