package com.booking.tickets.dao.impl;

import com.booking.tickets.dao.TicketDao;
import com.booking.tickets.model.Ticket;
import java.util.List;
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
        LOGGER.info(element + " was inserted to DB");
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
