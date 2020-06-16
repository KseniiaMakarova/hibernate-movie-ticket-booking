package com.booking.tickets;

import com.booking.tickets.config.AppConfig;
import com.booking.tickets.entity.cinemahall.model.CinemaHall;
import com.booking.tickets.entity.cinemahall.service.CinemaHallService;
import com.booking.tickets.entity.movie.model.Movie;
import com.booking.tickets.entity.movie.service.MovieService;
import com.booking.tickets.entity.moviesession.model.MovieSession;
import com.booking.tickets.entity.moviesession.service.MovieSessionService;
import com.booking.tickets.entity.order.service.OrderService;
import com.booking.tickets.entity.role.model.Role;
import com.booking.tickets.entity.role.service.RoleService;
import com.booking.tickets.entity.shoppingcart.model.ShoppingCart;
import com.booking.tickets.entity.shoppingcart.service.ShoppingCartService;
import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.security.service.AuthenticationService;
import com.booking.tickets.entity.user.service.UserService;
import com.booking.tickets.exception.AuthenticationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final AnnotationConfigApplicationContext context
            = new AnnotationConfigApplicationContext(AppConfig.class);
    private static final MovieService movieService
            = context.getBean(MovieService.class);
    private static final MovieSessionService movieSessionService
            = context.getBean(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService
            = context.getBean(CinemaHallService.class);
    private static final AuthenticationService authenticationService
            = context.getBean(AuthenticationService.class);
    private static final UserService userService
            = context.getBean(UserService.class);
    private static final ShoppingCartService shoppingCartService
            = context.getBean(ShoppingCartService.class);
    private static final OrderService orderService
            = context.getBean(OrderService.class);
    private static final RoleService roleService
            = context.getBean(RoleService.class);

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
        List<MovieSession> availableSessions
                = movieSessionService.findAvailableSessions(jojoRabbit.getId(), today);
        availableSessions.forEach(System.out::println);

        Role userRole = new Role();
        userRole.setName(Role.RoleName.USER);
        roleService.add(userRole);
        Role adminRole = new Role();
        adminRole.setName(Role.RoleName.ADMIN);
        roleService.add(adminRole);

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

        User user = userService.findByEmail("kseniia.makarova.kyiv@gmail.com");
        shoppingCartService.addSession(availableSessions.get(0), user);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        System.out.println(shoppingCart);

        orderService.completeOrder(shoppingCart.getTickets(), user);
        orderService.getOrderHistory(user).forEach(System.out::println);
        System.out.println(shoppingCartService.getByUser(user));
    }
}
