package com.booking.tickets.entity.cinemahall.service;

import com.booking.tickets.entity.cinemahall.model.CinemaHall;
import com.booking.tickets.entity.cinemahall.repository.CinemaHallDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    private final CinemaHallDao cinemaHallDao;

    public CinemaHallServiceImpl(CinemaHallDao cinemaHallDao) {
        this.cinemaHallDao = cinemaHallDao;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHallDao.get(id);
    }
}
