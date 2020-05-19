package com.booking.tickets.service.impl;

import com.booking.tickets.dao.MovieDao;
import com.booking.tickets.lib.Inject;
import com.booking.tickets.lib.Service;
import com.booking.tickets.model.Movie;
import com.booking.tickets.service.MovieService;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Inject
    MovieDao movieDao;

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
