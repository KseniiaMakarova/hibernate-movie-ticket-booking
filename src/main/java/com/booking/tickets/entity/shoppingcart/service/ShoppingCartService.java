package com.booking.tickets.entity.shoppingcart.service;

import com.booking.tickets.entity.moviesession.model.MovieSession;
import com.booking.tickets.entity.shoppingcart.model.ShoppingCart;
import com.booking.tickets.entity.user.model.User;

public interface ShoppingCartService {
    void addSession(MovieSession movieSession, User user);

    ShoppingCart getByUser(User user);

    void registerNewShoppingCart(User user);

    void clear(ShoppingCart shoppingCart);
}
