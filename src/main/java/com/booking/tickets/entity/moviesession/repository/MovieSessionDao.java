package com.booking.tickets.entity.moviesession.repository;

import com.booking.tickets.dao.GenericDao;
import com.booking.tickets.entity.moviesession.model.MovieSession;
import java.time.LocalDate;
import java.util.List;

public interface MovieSessionDao extends GenericDao<MovieSession> {
    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
