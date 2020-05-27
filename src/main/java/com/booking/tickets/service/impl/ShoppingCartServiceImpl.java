package com.booking.tickets.service.impl;

import com.booking.tickets.dao.ShoppingCartDao;
import com.booking.tickets.dao.TicketDao;
import com.booking.tickets.lib.Inject;
import com.booking.tickets.lib.Service;
import com.booking.tickets.model.MovieSession;
import com.booking.tickets.model.ShoppingCart;
import com.booking.tickets.model.Ticket;
import com.booking.tickets.model.User;
import com.booking.tickets.service.ShoppingCartService;
import java.util.Collections;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;
    @Inject
    private TicketDao ticketDao;

    @Override
    public void addSession(MovieSession movieSession, User user) {
        Ticket ticket = new Ticket();
        ticket.setMovieSession(movieSession);
        ticket.setUser(user);
        Ticket ticketFromDb = ticketDao.add(ticket);
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user);
        shoppingCart.getTickets().add(ticketFromDb);
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartDao.add(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.setTickets(Collections.emptyList());
        shoppingCartDao.update(shoppingCart);
    }
}
