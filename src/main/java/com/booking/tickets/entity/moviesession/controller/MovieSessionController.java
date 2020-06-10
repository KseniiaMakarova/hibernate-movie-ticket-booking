package com.booking.tickets.entity.moviesession.controller;

import com.booking.tickets.entity.cinemahall.service.CinemaHallService;
import com.booking.tickets.entity.movie.service.MovieService;
import com.booking.tickets.entity.moviesession.model.MovieSession;
import com.booking.tickets.entity.moviesession.model.dto.MovieSessionRequestDto;
import com.booking.tickets.entity.moviesession.model.dto.MovieSessionResponseDto;
import com.booking.tickets.entity.moviesession.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;
    private final MovieSessionService movieSessionService;

    public MovieSessionController(MovieService movieService, CinemaHallService cinemaHallService,
                                  MovieSessionService movieSessionService) {
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
        this.movieSessionService = movieSessionService;
    }

    @PostMapping
    public void addMovieSession(@RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        movieSessionService.add(movieSessionRequestDtoToMovieSession(movieSessionRequestDto));
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getAllAvailableSessions(Long movieId, String date) {
        return movieSessionService.findAvailableSessions(movieId, LocalDate.parse(date))
                .stream()
                .map(this::movieSessionToMovieSessionResponseDto)
                .collect(Collectors.toList());
    }

    private MovieSession movieSessionRequestDtoToMovieSession(
            MovieSessionRequestDto movieSessionRequestDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(
                movieService.get(movieSessionRequestDto.getMovieId()));
        movieSession.setCinemaHall(
                cinemaHallService.get(movieSessionRequestDto.getCinemaHallId()));
        movieSession.setSessionTime(
                LocalDateTime.parse(movieSessionRequestDto.getSessionTime()));
        return movieSession;
    }

    private MovieSessionResponseDto movieSessionToMovieSessionResponseDto(
            MovieSession movieSession) {
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
