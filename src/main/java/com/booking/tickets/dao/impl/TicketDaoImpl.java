package com.booking.tickets.dao.impl;

import com.booking.tickets.dao.TicketDao;
import com.booking.tickets.exception.DataProcessingException;
import com.booking.tickets.lib.Dao;
import com.booking.tickets.model.Ticket;
import com.booking.tickets.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class TicketDaoImpl implements TicketDao {
    private static final Logger LOGGER = LogManager.getLogger(TicketDaoImpl.class);

    @Override
    public Ticket add(Ticket ticket) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(ticket);
            transaction.commit();
            LOGGER.info(ticket + " was inserted to DB");
            return ticket;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("There was an error inserting "
                    + ticket, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
