package com.booking.tickets.service.impl;

import com.booking.tickets.dao.OrderDao;
import com.booking.tickets.lib.Inject;
import com.booking.tickets.lib.Service;
import com.booking.tickets.model.Order;
import com.booking.tickets.model.ShoppingCart;
import com.booking.tickets.model.Ticket;
import com.booking.tickets.model.User;
import com.booking.tickets.service.OrderService;
import com.booking.tickets.service.ShoppingCartService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Ticket> tickets, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setTickets(tickets);
        order.setOrderDate(LocalDateTime.now());
        Order orderFromDb = orderDao.add(order);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        shoppingCartService.clear(shoppingCart);
        return orderFromDb;
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getOrderHistory(user);
    }
}
