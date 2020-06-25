package com.booking.tickets.entity.user.security.controller;

import com.booking.tickets.entity.user.model.User;
import com.booking.tickets.entity.user.model.dto.UserDtoMapper;
import com.booking.tickets.entity.user.model.dto.UserLoginDto;
import com.booking.tickets.entity.user.model.dto.UserRegistrationDto;
import com.booking.tickets.entity.user.model.dto.UserResponseDto;
import com.booking.tickets.entity.user.security.jwt.JwtTokenProvider;
import com.booking.tickets.entity.user.security.service.AuthenticationService;
import com.booking.tickets.exception.WrongCredentialsAuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDtoMapper userDtoMapper;

    public AuthenticationController(AuthenticationService authenticationService,
                                    JwtTokenProvider jwtTokenProvider,
                                    UserDtoMapper userDtoMapper) {
        this.authenticationService = authenticationService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDtoMapper = userDtoMapper;
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        User user = authenticationService.register(
                userRegistrationDto.getLogin(), userRegistrationDto.getPassword());
        return userDtoMapper.toResponseDto(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto)
            throws WrongCredentialsAuthenticationException {
        User user = authenticationService.login(
                userLoginDto.getLogin(), userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getLogin(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toList()));
        Map<String, Object> body = new HashMap<>();
        body.put("username", user.getLogin());
        body.put("token", token);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
