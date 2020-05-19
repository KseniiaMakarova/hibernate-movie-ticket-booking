package com.booking.tickets.dao.impl;

import com.booking.tickets.dao.MovieDao;
import com.booking.tickets.exception.DataProcessingException;
import com.booking.tickets.lib.Dao;
import com.booking.tickets.model.Movie;
import com.booking.tickets.util.HibernateUtil;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieDaoImpl implements MovieDao {
    private static final Logger LOGGER = LogManager.getLogger(MovieDaoImpl.class);

    @Override
    public Movie add(Movie movie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(movie);
            transaction.commit();
            movie.setId(id);
            LOGGER.info(movie + " was inserted to DB");
            return movie;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("There was an error inserting " + movie, e);
        }
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaQuery<Movie> criteriaQuery
                    = session.getCriteriaBuilder().createQuery(Movie.class);
            criteriaQuery.from(Movie.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("There was an error retrieving all movies", e);
        }
    }
}
