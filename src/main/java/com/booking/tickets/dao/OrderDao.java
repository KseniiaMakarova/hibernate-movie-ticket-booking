package com.booking.tickets.dao;

import com.booking.tickets.model.Order;
import com.booking.tickets.model.User;
import java.util.List;

public interface OrderDao {
    Order add(Order order);

    List<Order> getOrderHistory(User user);
}
