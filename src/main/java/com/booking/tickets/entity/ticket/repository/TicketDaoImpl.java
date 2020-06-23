package com.booking.tickets.entity.ticket.repository;

import com.booking.tickets.dao.GenericDaoImpl;
import com.booking.tickets.entity.ticket.model.Ticket;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDaoImpl extends GenericDaoImpl<Ticket> implements TicketDao {
    private static final Logger LOGGER = LogManager.getLogger(TicketDaoImpl.class);

    public TicketDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Ticket add(Ticket element) {
        Ticket ticket = super.add(element);
        LOGGER.log(Level.INFO, "{} was inserted to DB", element);
        return ticket;
    }

    @Override
    public List<Ticket> getAll() {
        return super.getAll(Ticket.class);
    }

    @Override
    public Ticket get(Long id) {
        return super.get(Ticket.class, id);
    }
}
