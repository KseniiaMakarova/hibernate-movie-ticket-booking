package com.booking.tickets.dao;

import java.util.List;

public interface GenericDao<T> {
    T add(T element);

    List<T> getAll();
}
