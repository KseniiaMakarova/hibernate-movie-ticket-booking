package com.booking.tickets.entity.order.controller;

import com.booking.tickets.entity.order.model.Order;
import com.booking.tickets.entity.order.model.dto.OrderDtoMapper;
import com.booking.tickets.entity.order.model.dto.OrderResponseDto;
import com.booking.tickets.entity.order.service.OrderService;
import com.booking.tickets.entity.shoppingcart.model.ShoppingCart;
import com.booking.tickets.entity.shoppingcart.service.ShoppingCartService;
import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;

    public OrderController(UserService userService, ShoppingCartService shoppingCartService,
                           OrderService orderService, OrderDtoMapper orderDtoMapper) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
        this.orderDtoMapper = orderDtoMapper;
    }

    @PostMapping("/complete")
    public OrderResponseDto completeOrder(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        Order order = orderService.completeOrder(shoppingCart.getTickets(), user);
        return orderDtoMapper.toResponseDto(order);
    }

    @GetMapping
    public List<OrderResponseDto> getOrderHistoryOfUser(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        return orderService.getOrderHistory(user)
                .stream()
                .map(orderDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
