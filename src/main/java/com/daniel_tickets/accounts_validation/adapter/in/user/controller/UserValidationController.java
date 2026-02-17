package com.daniel_tickets.accounts_validation.adapter.in.user.controller;

import com.daniel_tickets.accounts_validation.application.UserValidationUseCase;
import com.daniel_tickets.accounts_validation.domain.user.model.User;
import com.daniel_tickets.accounts_validation.port.fifa.FifaValidationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/validation")
public class UserValidationController {

    private final FifaValidationPort fifaValidationPort;

    private final UserValidationUseCase userValidationUseCase;

    @PostMapping
    public void validate() {
        fifaValidationPort.isValid(User.builder().email("davilucas.d82@sisatdatagrouping.com").password("KjpIJZ^aQw-+di(Z&0C1EGZ").build());
//        userValidationUseCase.processUsersValidation();
    }

}
