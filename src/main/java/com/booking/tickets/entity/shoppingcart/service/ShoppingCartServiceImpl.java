package com.booking.tickets.entity.shoppingcart.service;

import com.booking.tickets.entity.moviesession.model.MovieSession;
import com.booking.tickets.entity.shoppingcart.model.ShoppingCart;
import com.booking.tickets.entity.shoppingcart.repository.ShoppingCartDao;
import com.booking.tickets.entity.ticket.model.Ticket;
import com.booking.tickets.entity.ticket.repository.TicketDao;
import com.booking.tickets.entity.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartDao shoppingCartDao;
    private final TicketDao ticketDao;

    public ShoppingCartServiceImpl(ShoppingCartDao shoppingCartDao, TicketDao ticketDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.ticketDao = ticketDao;
    }

    @Override
    public void addSession(MovieSession movieSession, User user) {
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user);
        Ticket ticketFromDb = ticketDao.add(createTicket(movieSession, user));
        shoppingCart.getTickets().add(ticketFromDb);
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user);
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartDao.add(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getTickets().clear();
        shoppingCartDao.update(shoppingCart);
    }

    private Ticket createTicket(MovieSession movieSession, User user) {
        Ticket ticket = new Ticket();
        ticket.setMovieSession(movieSession);
        ticket.setUser(user);
        return ticket;
    }
}
