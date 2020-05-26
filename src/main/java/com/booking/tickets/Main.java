package com.booking.tickets;

import com.booking.tickets.exception.AuthenticationException;
import com.booking.tickets.lib.Injector;
import com.booking.tickets.model.CinemaHall;
import com.booking.tickets.model.Movie;
import com.booking.tickets.model.MovieSession;
import com.booking.tickets.security.AuthenticationService;
import com.booking.tickets.service.CinemaHallService;
import com.booking.tickets.service.MovieService;
import com.booking.tickets.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final Injector INJECTOR = Injector.getInstance("com.booking.tickets");
    private static MovieService movieService
            = (MovieService) INJECTOR.getInstance(MovieService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
    private static AuthenticationService authenticationService
            = (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);

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
        parasiteSession.setSessionTime(LocalDateTime.of(2007, 1, 19, 18, 30));
        parasiteSession.setMovie(parasite);
        parasiteSession.setCinemaHall(blueCinemaHall);
        movieSessionService.add(parasiteSession);

        MovieSession jojoRabbitSession = new MovieSession();
        jojoRabbitSession.setSessionTime(LocalDateTime.of(2020, 5, 21, 21, 0));
        jojoRabbitSession.setMovie(jojoRabbit);
        jojoRabbitSession.setCinemaHall(redCinemaHall);
        movieSessionService.add(jojoRabbitSession);

        LocalDate today = LocalDate.of(2020, 5, 21);
        movieSessionService.findAvailableSessions(parasite.getId(), today)
                .forEach(System.out::println);
        movieSessionService.findAvailableSessions(jojoRabbit.getId(), today)
                .forEach(System.out::println);

        authenticationService.register("kseniia.makarova.kyiv@gmail.com", "12345678");
        try {
            authenticationService.login("some.other.guy@gmail.com", "12345678");
        } catch (AuthenticationException e) {
            LOGGER.warn("Authentication failed: " + e.getMessage());
        }
        try {
            authenticationService.login("kseniia.makarova.kyiv@gmail.com", "wrong");
        } catch (AuthenticationException e) {
            LOGGER.warn("Authentication failed: " + e.getMessage());
        }
        try {
            authenticationService.login("kseniia.makarova.kyiv@gmail.com", "12345678");
            System.out.println("Everything is OK");
        } catch (AuthenticationException e) {
            LOGGER.warn("Authentication failed: " + e.getMessage());
        }
    }
}
