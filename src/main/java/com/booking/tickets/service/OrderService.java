package com.booking.tickets.service;

import com.booking.tickets.model.Order;
import com.booking.tickets.model.Ticket;
import com.booking.tickets.model.User;
import java.util.List;

public interface OrderService {
    Order completeOrder(List<Ticket> tickets, User user);

    List<Order> getOrderHistory(User user);
}
