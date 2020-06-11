package com.booking.tickets.entity.order.controller;

import com.booking.tickets.entity.order.model.dto.OrderDtoMapper;
import com.booking.tickets.entity.order.model.dto.OrderRequestDto;
import com.booking.tickets.entity.order.model.dto.OrderResponseDto;
import com.booking.tickets.entity.order.service.OrderService;
import com.booking.tickets.entity.shoppingcart.model.ShoppingCart;
import com.booking.tickets.entity.shoppingcart.service.ShoppingCartService;
import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public void completeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        User user = userService.get(orderRequestDto.getUserId());
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        orderService.completeOrder(shoppingCart.getTickets(), user);
    }

    @GetMapping
    public List<OrderResponseDto> getOrderHistoryOfUser(Long userId) {
        User user = userService.get(userId);
        return orderService.getOrderHistory(user)
                .stream()
                .map(orderDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
