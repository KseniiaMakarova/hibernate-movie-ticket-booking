package com.booking.tickets;

import com.booking.tickets.exception.AuthenticationException;
import com.booking.tickets.lib.Injector;
import com.booking.tickets.model.CinemaHall;
import com.booking.tickets.model.Movie;
import com.booking.tickets.model.MovieSession;
import com.booking.tickets.model.Ticket;
import com.booking.tickets.model.User;
import com.booking.tickets.security.AuthenticationService;
import com.booking.tickets.service.CinemaHallService;
import com.booking.tickets.service.MovieService;
import com.booking.tickets.service.MovieSessionService;
import com.booking.tickets.service.OrderService;
import com.booking.tickets.service.ShoppingCartService;
import com.booking.tickets.service.UserService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ServiceTest {
    private static MovieService movieService;
    private static MovieSessionService movieSessionService;
    private static CinemaHallService cinemaHallService;
    private static AuthenticationService authenticationService;
    private static UserService userService;
    private static ShoppingCartService shoppingCartService;
    private static OrderService orderService;
    private static List<Movie> movies;
    private static List<CinemaHall> cinemaHalls;
    private static List<MovieSession> movieSessions;
    private static List<User> users;

    @BeforeClass
    public static void beforeClass() {
        Injector injector = Injector.getInstance("com.booking.tickets");
        movieService = (MovieService) injector.getInstance(MovieService.class);
        cinemaHallService = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);
        userService = (UserService) injector.getInstance(UserService.class);
        shoppingCartService
                = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        orderService = (OrderService) injector.getInstance(OrderService.class);
        createMovies();
        createCinemaHalls();
        createMovieSessions();
        createUsers();
    }

    private static void createMovies() {
        Movie parasite = new Movie();
        parasite.setTitle("Parasite");
        parasite.setDescription("Just another overrated nonsense, period");
        Movie jojoRabbit = new Movie();
        jojoRabbit.setTitle("Jojo Rabbit");
        jojoRabbit.setDescription(
                "Sometimes comedy can be more effective than drama when it has something to say");
        movies = List.of(parasite, jojoRabbit);
        movies.forEach(movie -> movieService.add(movie));
    }

    private static void createCinemaHalls() {
        CinemaHall blueCinemaHall = new CinemaHall();
        blueCinemaHall.setCapacity(100);
        blueCinemaHall.setDescription("blue");
        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setCapacity(75);
        redCinemaHall.setDescription("red");
        cinemaHalls = List.of(blueCinemaHall, redCinemaHall);
        cinemaHalls.forEach(cinemaHall -> cinemaHallService.add(cinemaHall));
    }

    private static void createMovieSessions() {
        MovieSession parasiteSession = new MovieSession();
        parasiteSession.setSessionTime(LocalDateTime.of(2007, 1, 19, 18, 30));
        parasiteSession.setMovie(movies.get(0));
        parasiteSession.setCinemaHall(cinemaHalls.get(0));
        MovieSession jojoRabbitSession = new MovieSession();
        jojoRabbitSession.setSessionTime(LocalDateTime.of(2020, 5, 21, 21, 0));
        jojoRabbitSession.setMovie(movies.get(1));
        jojoRabbitSession.setCinemaHall(cinemaHalls.get(1));
        movieSessions = List.of(parasiteSession, jojoRabbitSession);
        movieSessions.forEach(movieSession -> movieSessionService.add(movieSession));
    }

    private static void createUsers() {
        User user = authenticationService.register("kseniia.makarova.kyiv@gmail.com", "12345678");
        users = List.of(user);
    }

    @Test
    public void checkMoviesCount() {
        Assert.assertEquals(2, movieService.getAll().size());
    }

    @Test
    public void checkCinemaHallsCount() {
        Assert.assertEquals(2, cinemaHallService.getAll().size());
    }

    @Test
    public void checkAvailableSessionsCount() {
        LocalDate today = LocalDate.of(2020, 5, 21);
        Movie jojoRabbit = movies.get(1);
        Movie parasite = movies.get(0);
        Assert.assertEquals(1,
                movieSessionService.findAvailableSessions(jojoRabbit.getId(), today).size());
        Assert.assertEquals(0,
                movieSessionService.findAvailableSessions(parasite.getId(), today).size());
    }

    @Test
    public void checkAvailableSessions() {
        LocalDate today = LocalDate.of(2020, 5, 21);
        MovieSession jojoRabbitSession = movieSessions.get(1);
        Movie jojoRabbit = movies.get(1);
        Assert.assertEquals(jojoRabbitSession,
                movieSessionService.findAvailableSessions(jojoRabbit.getId(), today).get(0));
    }

    @Test(expected = AuthenticationException.class)
    public void checkLoginWrongEmail() throws AuthenticationException {
        authenticationService.login("some.other.guy@gmail.com", "12345678");
    }

    @Test(expected = AuthenticationException.class)
    public void checkLoginWrongPassword() throws AuthenticationException {
        authenticationService.login("kseniia.makarova.kyiv@gmail.com", "wrong");
    }

    @Test
    public void checkLoginCorrectData() throws AuthenticationException {
        authenticationService.login("kseniia.makarova.kyiv@gmail.com", "12345678");
    }

    @Test
    public void checkFindUser() {
        User user = users.get(0);
        Assert.assertEquals(user, userService.findByEmail("kseniia.makarova.kyiv@gmail.com"));
    }

    @Test
    public void checkShoppingCartNotNull() {
        User user = users.get(0);
        Assert.assertNotNull(shoppingCartService.getByUser(user));
    }

    @Test
    public void checkShoppingCartUserRelation() {
        User user = users.get(0);
        Assert.assertEquals(shoppingCartService.getByUser(user).getUser(), user);
    }

    @Test
    public void checkAddTickets() {
        User user = users.get(0);
        MovieSession jojoRabbitSession = movieSessions.get(1);
        List<Ticket> tickets = shoppingCartService.getByUser(user).getTickets();
        Assert.assertEquals(0, tickets.size());
        shoppingCartService.addSession(jojoRabbitSession, user);
        tickets = shoppingCartService.getByUser(user).getTickets();
        Assert.assertEquals(1, tickets.size());
    }

    @Test
    public void checkClearShoppingCart() {
        User user = users.get(0);
        MovieSession jojoRabbitSession = movieSessions.get(1);
        shoppingCartService.addSession(jojoRabbitSession, user);
        List<Ticket> tickets = shoppingCartService.getByUser(user).getTickets();
        Assert.assertNotEquals(0, tickets.size());
        shoppingCartService.clear(shoppingCartService.getByUser(user));
        tickets = shoppingCartService.getByUser(user).getTickets();
        Assert.assertEquals(0, tickets.size());
    }

    @Test
    public void checkOrderHistory() {
        User user = users.get(0);
        MovieSession jojoRabbitSession = movieSessions.get(1);
        shoppingCartService.addSession(jojoRabbitSession, user);
        List<Ticket> tickets = shoppingCartService.getByUser(user).getTickets();
        Assert.assertEquals(0, orderService.getOrderHistory(user).size());
        orderService.completeOrder(tickets, user);
        Assert.assertEquals(1, orderService.getOrderHistory(user).size());
    }
}
