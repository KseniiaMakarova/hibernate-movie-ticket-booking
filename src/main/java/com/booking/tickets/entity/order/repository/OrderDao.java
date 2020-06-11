package com.booking.tickets.entity.order.repository;

import com.booking.tickets.dao.GenericDao;
import com.booking.tickets.entity.order.model.Order;
import com.booking.tickets.entity.user.model.User;
import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> getOrderHistory(User user);
}
