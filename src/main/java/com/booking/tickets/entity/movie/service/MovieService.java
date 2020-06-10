package com.booking.tickets.entity.movie.service;

import com.booking.tickets.entity.movie.model.Movie;
import java.util.List;

public interface MovieService {
    Movie add(Movie movie);

    List<Movie> getAll();

    Movie get(Long id);
}
