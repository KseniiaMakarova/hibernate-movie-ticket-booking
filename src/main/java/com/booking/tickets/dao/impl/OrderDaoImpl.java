package com.booking.tickets.dao.impl;

import com.booking.tickets.dao.OrderDao;
import com.booking.tickets.exception.DataProcessingException;
import com.booking.tickets.model.Order;
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
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Order> implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger(OrderDaoImpl.class);
    private final SessionFactory sessionFactory;

    public OrderDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order add(Order element) {
        Order order = super.add(element);
        LOGGER.info(element + " was inserted to DB");
        return order;
    }

    @Override
    public List<Order> getAll() {
        return super.getAll(Order.class);
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> query
                    = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = query.from(Order.class);
            root.fetch("tickets", JoinType.LEFT);
            return session.createQuery(
                    query.distinct(true).where(criteriaBuilder.equal(root.get("user"), user)))
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("There was an error retrieving all orders of user "
                    + user, e);
        }
    }
}
