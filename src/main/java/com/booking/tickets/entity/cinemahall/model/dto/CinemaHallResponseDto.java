package com.booking.tickets.entity.cinemahall.model.dto;

public class CinemaHallResponseDto {
    private Long id;
    private int capacity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
