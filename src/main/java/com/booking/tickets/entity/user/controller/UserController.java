package com.booking.tickets.entity.user.controller;

import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.model.dto.UserDtoMapper;
import com.booking.tickets.entity.user.model.dto.UserResponseDto;
import com.booking.tickets.entity.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    public UserController(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/by-email/{email:.+}")
    public UserResponseDto getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return userDtoMapper.toResponseDto(user);
    }
}
