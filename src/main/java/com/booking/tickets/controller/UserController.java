package com.booking.tickets.controller;

import com.booking.tickets.model.User;
import com.booking.tickets.model.dto.response.UserResponseDto;
import com.booking.tickets.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/by-email/{email}")
    public UserResponseDto getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return userToUserResponseDto(user);
    }

    private UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setLogin(user.getLogin());
        return userResponseDto;
    }
}
