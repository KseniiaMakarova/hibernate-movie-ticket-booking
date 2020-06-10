package com.booking.tickets.service;

import com.booking.tickets.model.Movie;
import java.util.List;

public interface MovieService {
    Movie add(Movie movie);

    List<Movie> getAll();

    Movie get(Long id);
}
