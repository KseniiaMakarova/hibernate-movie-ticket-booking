package com.booking.tickets.entity.order.service;

import com.booking.tickets.entity.order.model.Order;
import com.booking.tickets.entity.ticket.model.Ticket;
import com.booking.tickets.entity.user.model.User;
import java.util.List;

public interface OrderService {
    Order completeOrder(List<Ticket> tickets, User user);

    List<Order> getOrderHistory(User user);
}
