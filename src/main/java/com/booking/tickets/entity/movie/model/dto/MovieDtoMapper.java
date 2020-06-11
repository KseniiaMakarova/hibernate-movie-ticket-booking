package com.booking.tickets.entity.movie.model.dto;

import com.booking.tickets.entity.movie.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieDtoMapper {
    public Movie fromRequestDto(MovieRequestDto movieRequestDto) {
        Movie movie = new Movie();
        movie.setTitle(movieRequestDto.getTitle());
        movie.setDescription(movieRequestDto.getDescription());
        return movie;
    }

    public MovieResponseDto toResponseDto(Movie movie) {
        MovieResponseDto movieResponseDto = new MovieResponseDto();
        movieResponseDto.setId(movie.getId());
        movieResponseDto.setTitle(movie.getTitle());
        return movieResponseDto;
    }
}
