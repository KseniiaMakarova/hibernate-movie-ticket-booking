package com.booking.tickets.entity.movie.controller;

import com.booking.tickets.entity.movie.model.dto.MovieDtoMapper;
import com.booking.tickets.entity.movie.model.dto.MovieRequestDto;
import com.booking.tickets.entity.movie.model.dto.MovieResponseDto;
import com.booking.tickets.entity.movie.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    public final MovieService movieService;
    public final MovieDtoMapper movieDtoMapper;

    public MovieController(MovieService movieService, MovieDtoMapper movieDtoMapper) {
        this.movieService = movieService;
        this.movieDtoMapper = movieDtoMapper;
    }

    @PostMapping
    public void addMovie(@RequestBody MovieRequestDto movieRequestDto) {
        movieService.add(movieDtoMapper.fromRequestDto(movieRequestDto));
    }

    @GetMapping
    public List<MovieResponseDto> getAllMovies() {
        return movieService.getAll()
                .stream()
                .map(movieDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
