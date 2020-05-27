package com.booking.tickets.dao;

import com.booking.tickets.model.ShoppingCart;
import com.booking.tickets.model.User;

public interface ShoppingCartDao {
    ShoppingCart add(ShoppingCart shoppingCart);

    ShoppingCart getByUser(User user);

    void update(ShoppingCart shoppingCart);
}
