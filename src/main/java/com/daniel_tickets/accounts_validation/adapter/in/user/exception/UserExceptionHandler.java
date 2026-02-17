package com.daniel_tickets.accounts_validation.adapter.in.user.exception;

import com.daniel_tickets.accounts_validation.adapter.out.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
    }

}
