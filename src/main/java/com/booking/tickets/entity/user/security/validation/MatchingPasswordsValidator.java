package com.booking.tickets.entity.user.security.validation;

import com.booking.tickets.entity.user.model.dto.UserRequestDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class MatchingPasswordsValidator
        implements ConstraintValidator<MatchingPasswordsConstraint, UserRequestDto> {
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 30;
    private String password;
    private String repeatedPassword;

    @Override
    public void initialize(MatchingPasswordsConstraint constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.repeatedPassword = constraintAnnotation.repeatedPassword();
    }

    @Override
    public boolean isValid(UserRequestDto userRequestDto, ConstraintValidatorContext context) {
        String passwordValue
                = (String) new BeanWrapperImpl(userRequestDto).getPropertyValue(password);
        String repeatedPasswordValue
                = (String) new BeanWrapperImpl(userRequestDto).getPropertyValue(repeatedPassword);
        return passwordValue != null && passwordValue.equals(repeatedPasswordValue)
                && passwordValue.length() >= MIN_LENGTH && passwordValue.length() <= MAX_LENGTH;
    }
}
