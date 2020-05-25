package com.booking.tickets.dao.impl;

import com.booking.tickets.dao.UserDao;
import com.booking.tickets.exception.DataProcessingException;
import com.booking.tickets.lib.Dao;
import com.booking.tickets.model.User;
import com.booking.tickets.util.HibernateUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            LOGGER.info(user + " was inserted to DB");
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("There was an error inserting "
                    + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> query
                    = criteriaBuilder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            return session.createQuery(
                    query.where(criteriaBuilder.equal(root.get("login"), email)))
                    .uniqueResult();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "There was an error retrieving a user with email " + email, e);
        }
    }
}
