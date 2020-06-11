package com.booking.tickets.entity.moviesession.model.dto;

import com.booking.tickets.entity.cinemahall.service.CinemaHallService;
import com.booking.tickets.entity.movie.service.MovieService;
import com.booking.tickets.entity.moviesession.model.MovieSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class MovieSessionDtoMapper {
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;

    public MovieSessionDtoMapper(MovieService movieService, CinemaHallService cinemaHallService) {
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
    }

    public MovieSession fromRequestDto(MovieSessionRequestDto movieSessionRequestDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(
                movieService.get(movieSessionRequestDto.getMovieId()));
        movieSession.setCinemaHall(
                cinemaHallService.get(movieSessionRequestDto.getCinemaHallId()));
        movieSession.setSessionTime(
                LocalDateTime.parse(movieSessionRequestDto.getSessionTime()));
        return movieSession;
    }

    public MovieSessionResponseDto toResponseDto(MovieSession movieSession) {
        MovieSessionResponseDto movieSessionResponseDto = new MovieSessionResponseDto();
        movieSessionResponseDto.setId(movieSession.getId());
        movieSessionResponseDto.setMovieId(movieSession.getMovie().getId());
        movieSessionResponseDto.setCinemaHallId(movieSession.getCinemaHall().getId());
        LocalDateTime sessionTime = movieSession.getSessionTime();
        movieSessionResponseDto.setSessionDate(
                sessionTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
        movieSessionResponseDto.setSessionTime(
                sessionTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
        return movieSessionResponseDto;
    }
}
