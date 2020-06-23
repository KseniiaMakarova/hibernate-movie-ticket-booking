package com.booking.tickets.entity.user.repository;

import com.booking.tickets.dao.GenericDaoImpl;
import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.exception.DataProcessingException;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User add(User element) {
        User user = super.add(element);
        LOGGER.log(Level.INFO, "{} was inserted to DB", element);
        return user;
    }

    @Override
    public List<User> getAll() {
        return super.getAll(User.class);
    }

    @Override
    public User get(Long id) {
        return super.get(User.class, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> query
                    = criteriaBuilder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            root.fetch("roles", JoinType.LEFT);
            return session.createQuery(
                    query.distinct(true).where(criteriaBuilder.equal(root.get("login"), email)))
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "There was an error retrieving a user with email " + email, e);
        }
    }
}
