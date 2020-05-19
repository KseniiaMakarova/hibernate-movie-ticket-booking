package com.booking.tickets.dao;

import com.booking.tickets.model.Movie;
import java.util.List;

public interface MovieDao {
    Movie add(Movie movie);

    List<Movie> getAll();
}
