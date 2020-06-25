package com.booking.tickets.entity.user.model.dto;

import com.booking.tickets.entity.user.security.validation.EmailConstraint;

public class UserLoginDto {
    @EmailConstraint
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
