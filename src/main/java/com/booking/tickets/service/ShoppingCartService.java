package com.booking.tickets.service;

import com.booking.tickets.model.MovieSession;
import com.booking.tickets.model.ShoppingCart;
import com.booking.tickets.model.User;

public interface ShoppingCartService {
    void addSession(MovieSession movieSession, User user);

    ShoppingCart getByUser(User user);

    void registerNewShoppingCart(User user);

    void clear(ShoppingCart shoppingCart);
}
