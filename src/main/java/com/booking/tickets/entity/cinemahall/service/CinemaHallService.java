package com.booking.tickets.entity.cinemahall.service;

import com.booking.tickets.entity.cinemahall.model.CinemaHall;
import java.util.List;

public interface CinemaHallService {
    CinemaHall add(CinemaHall cinemaHall);

    List<CinemaHall> getAll();

    CinemaHall get(Long id);
}
