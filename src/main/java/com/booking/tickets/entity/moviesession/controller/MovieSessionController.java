package com.booking.tickets.entity.moviesession.controller;

import com.booking.tickets.entity.moviesession.model.dto.MovieSessionDtoMapper;
import com.booking.tickets.entity.moviesession.model.dto.MovieSessionRequestDto;
import com.booking.tickets.entity.moviesession.model.dto.MovieSessionResponseDto;
import com.booking.tickets.entity.moviesession.service.MovieSessionService;
import java.time.LocalDate;
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
    private final MovieSessionService movieSessionService;
    private final MovieSessionDtoMapper movieSessionDtoMapper;

    public MovieSessionController(
            MovieSessionService movieSessionService, MovieSessionDtoMapper movieSessionDtoMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionDtoMapper = movieSessionDtoMapper;
    }

    @PostMapping
    public void addMovieSession(@RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        movieSessionService.add(movieSessionDtoMapper.fromRequestDto(movieSessionRequestDto));
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getAllAvailableSessions(Long movieId, String date) {
        return movieSessionService.findAvailableSessions(movieId, LocalDate.parse(date))
                .stream()
                .map(movieSessionDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
