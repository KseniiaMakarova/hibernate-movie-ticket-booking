package com.booking.tickets;

import com.booking.tickets.lib.Injector;
import com.booking.tickets.model.Movie;
import com.booking.tickets.service.MovieService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("com.booking.tickets");

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        movieService.add(movie);

        movieService.getAll().forEach(System.out::println);
    }
}
