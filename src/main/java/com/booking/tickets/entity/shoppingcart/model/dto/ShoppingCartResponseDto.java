package com.booking.tickets.entity.shoppingcart.model.dto;

import java.util.List;

public class ShoppingCartResponseDto {
    private Long userId;
    private List<Long> ticketIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Long> ticketIds) {
        this.ticketIds = ticketIds;
    }
}
