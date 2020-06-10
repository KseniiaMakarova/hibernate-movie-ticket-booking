package com.booking.tickets.dao.impl;

import com.booking.tickets.dao.ShoppingCartDao;
import com.booking.tickets.exception.DataProcessingException;
import com.booking.tickets.model.ShoppingCart;
import com.booking.tickets.model.User;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl extends GenericDaoImpl<ShoppingCart> implements ShoppingCartDao {
    private static final Logger LOGGER = LogManager.getLogger(ShoppingCartDaoImpl.class);
    private final SessionFactory sessionFactory;

    public ShoppingCartDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShoppingCart add(ShoppingCart element) {
        ShoppingCart shoppingCart = super.add(element);
        LOGGER.info(element + " was inserted to DB");
        return shoppingCart;
    }

    @Override
    public List<ShoppingCart> getAll() {
        return super.getAll(ShoppingCart.class);
    }

    @Override
    public ShoppingCart get(Long id) {
        return super.get(ShoppingCart.class, id);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ShoppingCart> query
                    = criteriaBuilder.createQuery(ShoppingCart.class);
            Root<ShoppingCart> root = query.from(ShoppingCart.class);
            root.fetch("tickets", JoinType.LEFT);
            return session.createQuery(
                    query.where(criteriaBuilder.equal(root.get("user"), user)))
                    .getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "There was an error retrieving a shopping cart of user " + user, e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(shoppingCart);
            transaction.commit();
            LOGGER.info(shoppingCart + " was updated");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("There was an error updating "
                    + shoppingCart, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
