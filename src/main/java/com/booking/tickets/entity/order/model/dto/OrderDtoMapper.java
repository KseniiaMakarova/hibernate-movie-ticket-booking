package com.booking.tickets.entity.order.model.dto;

import com.booking.tickets.entity.order.model.Order;
import com.booking.tickets.entity.ticket.model.Ticket;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper {
    public OrderResponseDto toResponseDto(Order order) {
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
