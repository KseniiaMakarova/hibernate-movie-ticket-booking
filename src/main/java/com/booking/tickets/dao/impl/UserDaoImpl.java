package com.booking.tickets.dao.impl;

import com.booking.tickets.dao.UserDao;
import com.booking.tickets.exception.DataProcessingException;
import com.booking.tickets.model.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        LOGGER.info(element + " was inserted to DB");
        return user;
    }

    @Override
    public List<User> getAll() {
        return super.getAll(User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> query
                    = criteriaBuilder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            return session.createQuery(
                    query.where(criteriaBuilder.equal(root.get("login"), email)))
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "There was an error retrieving a user with email " + email, e);
        }
    }
}
