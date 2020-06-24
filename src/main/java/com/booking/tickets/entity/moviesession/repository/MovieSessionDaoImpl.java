package com.booking.tickets.entity.moviesession.repository;

import com.booking.tickets.dao.GenericDaoImpl;
import com.booking.tickets.entity.moviesession.model.MovieSession;
import com.booking.tickets.exception.DataProcessingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl extends GenericDaoImpl<MovieSession> implements MovieSessionDao {
    private static final Logger LOGGER = LogManager.getLogger(MovieSessionDaoImpl.class);
    private final SessionFactory sessionFactory;

    public MovieSessionDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public MovieSession add(MovieSession element) {
        MovieSession movieSession = super.add(element);
        LOGGER.log(Level.INFO, "{} was inserted to DB", element);
        return movieSession;
    }

    @Override
    public List<MovieSession> getAll() {
        return super.getAll(MovieSession.class);
    }

    @Override
    public MovieSession get(Long id) {
        return super.get(MovieSession.class, id);
    }

    @Override
    public List<MovieSession> getAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MovieSession> query
                    = criteriaBuilder.createQuery(MovieSession.class);
            Root<MovieSession> root = query.from(MovieSession.class);
            Predicate idPredicate
                    = criteriaBuilder.equal(root.get("movie"), movieId);
            Predicate datePredicate = criteriaBuilder.between(
                    root.get("sessionTime"), date.atStartOfDay(), date.atTime(LocalTime.MAX));
            return session.createQuery(query.where(idPredicate, datePredicate)).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "There was an error retrieving available sessions", e);
        }
    }
}
