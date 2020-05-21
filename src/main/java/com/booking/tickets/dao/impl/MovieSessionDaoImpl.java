package com.booking.tickets.dao.impl;

import com.booking.tickets.dao.MovieSessionDao;
import com.booking.tickets.exception.DataProcessingException;
import com.booking.tickets.lib.Dao;
import com.booking.tickets.model.MovieSession;
import com.booking.tickets.util.HibernateUtil;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger LOGGER = LogManager.getLogger(MovieSessionDaoImpl.class);

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MovieSession> query
                    = criteriaBuilder.createQuery(MovieSession.class);
            Root<MovieSession> root = query.from(MovieSession.class);
            Predicate idPredicate
                    = criteriaBuilder.equal(root.get("movie"), movieId);
            Predicate datePredicate
                    = criteriaBuilder.greaterThan(root.get("sessionTime"), date.atStartOfDay());
            return session.createQuery(query.where(idPredicate, datePredicate)).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "There was an error retrieving available sessions", e);
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            LOGGER.info(movieSession + " was inserted to DB");
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("There was an error inserting "
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
