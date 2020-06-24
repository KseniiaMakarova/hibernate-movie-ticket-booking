package com.booking.tickets.entity.shoppingcart.controller;

import com.booking.tickets.entity.moviesession.model.MovieSession;
import com.booking.tickets.entity.moviesession.service.MovieSessionService;
import com.booking.tickets.entity.shoppingcart.model.dto.ShoppingCartDtoMapper;
import com.booking.tickets.entity.shoppingcart.model.dto.ShoppingCartResponseDto;
import com.booking.tickets.entity.shoppingcart.service.ShoppingCartService;
import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.service.UserService;
import org.springframework.security.core.Authentication;
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
    private final ShoppingCartDtoMapper shoppingCartDtoMapper;

    public ShoppingCartController(MovieSessionService movieSessionService, UserService userService,
                                  ShoppingCartService shoppingCartService,
                                  ShoppingCartDtoMapper shoppingCartDtoMapper) {
        this.movieSessionService = movieSessionService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartDtoMapper = shoppingCartDtoMapper;
    }

    @PostMapping("/add-movie-session")
    public void addMovieSession(Long movieSessionId, Authentication authentication) {
        MovieSession movieSession = movieSessionService.get(movieSessionId);
        User user = userService.getByEmail(authentication.getName());
        shoppingCartService.addSession(movieSession, user);
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getShoppingCartByUser(Authentication authentication) {
        User user = userService.getByEmail(authentication.getName());
        return shoppingCartDtoMapper.toResponseDto(shoppingCartService.getByUser(user));
    }
}
