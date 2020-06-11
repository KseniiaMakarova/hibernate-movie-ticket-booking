package com.booking.tickets.entity.cinemahall.repository;

import com.booking.tickets.dao.GenericDaoImpl;
import com.booking.tickets.entity.cinemahall.model.CinemaHall;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CinemaHallDaoImpl extends GenericDaoImpl<CinemaHall> implements CinemaHallDao {
    private static final Logger LOGGER = LogManager.getLogger(CinemaHallDaoImpl.class);

    public CinemaHallDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public CinemaHall add(CinemaHall element) {
        CinemaHall cinemaHall = super.add(element);
        LOGGER.info(element + " was inserted to DB");
        return cinemaHall;
    }

    @Override
    public List<CinemaHall> getAll() {
        return super.getAll(CinemaHall.class);
    }

    @Override
    public CinemaHall get(Long id) {
        return super.get(CinemaHall.class, id);
    }
}
