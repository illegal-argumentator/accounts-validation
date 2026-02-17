package com.daniel_tickets.accounts_validation.application;

import com.daniel_tickets.accounts_validation.domain.user.model.User;
import com.daniel_tickets.accounts_validation.domain.user.type.Activity;
import com.daniel_tickets.accounts_validation.port.user.UserCommandPort;
import com.daniel_tickets.accounts_validation.port.user.UserQueryPort;
import com.daniel_tickets.accounts_validation.port.fifa.FifaValidationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationUseCase {

    private final UserQueryPort userQueryPort;

    private final UserCommandPort userCommandPort;

    private final FifaValidationPort fifaValidationPort;

    public void processUsersValidation() {
        List<User> users = userQueryPort.getAll(Activity.PENDING);
        users.forEach(this::startUserValidation);
    }

    public void startUserValidation(User user) {
        boolean isValid = fifaValidationPort.isValid(user);

        User updateUser = User.builder()
                .activity(isValid ? Activity.VALID : Activity.INVALID)
                .build();

        userCommandPort.update(user.email(), updateUser);
    }

}
