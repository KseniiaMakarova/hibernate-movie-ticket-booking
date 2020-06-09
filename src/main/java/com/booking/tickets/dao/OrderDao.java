package com.booking.tickets.dao;

import com.booking.tickets.model.Order;
import com.booking.tickets.model.User;
import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> getOrderHistory(User user);
}
