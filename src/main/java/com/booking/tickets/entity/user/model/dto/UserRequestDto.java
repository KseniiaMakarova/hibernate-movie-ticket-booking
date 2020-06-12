package com.booking.tickets.entity.user.model.dto;

import com.booking.tickets.entity.user.security.validation.EmailConstraint;
import com.booking.tickets.entity.user.security.validation.MatchingPasswordsConstraint;

@MatchingPasswordsConstraint(password = "password", repeatedPassword = "repeatedPassword")
public class UserRequestDto {
    @EmailConstraint
    private String login;
    private String password;
    private String repeatedPassword;

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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}
