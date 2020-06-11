package com.booking.tickets.entity.movie.service;

import com.booking.tickets.entity.movie.model.Movie;
import com.booking.tickets.entity.movie.repository.MovieDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieDao movieDao;

    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public Movie get(Long id) {
        return movieDao.get(id);
    }
}
