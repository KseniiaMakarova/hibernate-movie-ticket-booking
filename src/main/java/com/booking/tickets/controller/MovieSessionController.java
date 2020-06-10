package com.booking.tickets.controller;

import com.booking.tickets.model.MovieSession;
import com.booking.tickets.model.dto.request.MovieSessionRequestDto;
import com.booking.tickets.model.dto.response.MovieSessionResponseDto;
import com.booking.tickets.service.CinemaHallService;
import com.booking.tickets.service.MovieService;
import com.booking.tickets.service.MovieSessionService;
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
        movieSessionResponseDto.setSessionTime(
                movieSession.getSessionTime().format(DateTimeFormatter.ISO_LOCAL_DATE));
        return movieSessionResponseDto;
    }
}
