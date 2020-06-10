package com.booking.tickets.controller;

import com.booking.tickets.model.Order;
import com.booking.tickets.model.ShoppingCart;
import com.booking.tickets.model.Ticket;
import com.booking.tickets.model.User;
import com.booking.tickets.model.dto.request.OrderRequestDto;
import com.booking.tickets.model.dto.response.OrderResponseDto;
import com.booking.tickets.service.OrderService;
import com.booking.tickets.service.ShoppingCartService;
import com.booking.tickets.service.UserService;
import java.time.format.DateTimeFormatter;
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

    public OrderController(UserService userService, ShoppingCartService shoppingCartService,
                           OrderService orderService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
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
                .map(this::orderToOrderResponseDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto orderToOrderResponseDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setOrderDate(
                order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        orderResponseDto.setTicketIds(order.getTickets()
                .stream()
                .map(Ticket::getId)
                .collect(Collectors.toList()));
        return orderResponseDto;
    }
}
