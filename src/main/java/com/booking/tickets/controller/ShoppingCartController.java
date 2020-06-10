package com.booking.tickets.controller;

import com.booking.tickets.model.MovieSession;
import com.booking.tickets.model.ShoppingCart;
import com.booking.tickets.model.Ticket;
import com.booking.tickets.model.User;
import com.booking.tickets.model.dto.response.ShoppingCartResponseDto;
import com.booking.tickets.service.MovieSessionService;
import com.booking.tickets.service.ShoppingCartService;
import com.booking.tickets.service.UserService;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final MovieSessionService movieSessionService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(MovieSessionService movieSessionService, UserService userService,
                                  ShoppingCartService shoppingCartService) {
        this.movieSessionService = movieSessionService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/add-movie-session")
    public void addMovieSession(Long movieSessionId, Long userId) {
        MovieSession movieSession = movieSessionService.get(movieSessionId);
        User user = userService.get(userId);
        shoppingCartService.addSession(movieSession, user);
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getShoppingCartByUser(Long userId) {
        User user = userService.get(userId);
        return shoppingCartToShoppingCartResponseDto(shoppingCartService.getByUser(user));
    }

    private ShoppingCartResponseDto shoppingCartToShoppingCartResponseDto(
            ShoppingCart shoppingCart) {
        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setUserId(shoppingCart.getId());
        shoppingCartResponseDto.setTicketIds(shoppingCart.getTickets()
                .stream()
                .map(Ticket::getId)
                .collect(Collectors.toList()));
        return shoppingCartResponseDto;
    }
}
