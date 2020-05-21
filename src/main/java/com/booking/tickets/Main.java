package com.booking.tickets;

import com.booking.tickets.lib.Injector;
import com.booking.tickets.model.CinemaHall;
import com.booking.tickets.model.Movie;
import com.booking.tickets.model.MovieSession;
import com.booking.tickets.service.CinemaHallService;
import com.booking.tickets.service.MovieService;
import com.booking.tickets.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("com.booking.tickets");
    private static MovieService movieService
            = (MovieService) INJECTOR.getInstance(MovieService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie parasite = new Movie();
        parasite.setTitle("Parasite");
        parasite.setDescription("Just another overrated nonsense, period");
        movieService.add(parasite);

        Movie jojoRabbit = new Movie();
        jojoRabbit.setTitle("Jojo Rabbit");
        jojoRabbit.setDescription(
                "Sometimes comedy can be more effective than drama when it has something to say");
        movieService.add(jojoRabbit);

        movieService.getAll().forEach(System.out::println);

        CinemaHall blueCinemaHall = new CinemaHall();
        blueCinemaHall.setCapacity(100);
        blueCinemaHall.setDescription("blue");
        cinemaHallService.add(blueCinemaHall);

        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setCapacity(75);
        redCinemaHall.setDescription("red");
        cinemaHallService.add(redCinemaHall);

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession parasiteSession = new MovieSession();
        parasiteSession.setSessionTime(LocalDateTime.of(2020, 10, 24, 18, 30));
        parasiteSession.setMovie(parasite);
        parasiteSession.setCinemaHall(blueCinemaHall);
        movieSessionService.add(parasiteSession);

        MovieSession jojoRabbitSession = new MovieSession();
        jojoRabbitSession.setSessionTime(LocalDateTime.of(2007, 10, 24, 21, 30));
        jojoRabbitSession.setMovie(jojoRabbit);
        jojoRabbitSession.setCinemaHall(redCinemaHall);
        movieSessionService.add(jojoRabbitSession);

        movieSessionService.findAvailableSessions(parasite.getId(), LocalDate.now())
                .forEach(System.out::println);
        movieSessionService.findAvailableSessions(jojoRabbit.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
