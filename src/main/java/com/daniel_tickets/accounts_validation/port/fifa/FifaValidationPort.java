package com.daniel_tickets.accounts_validation.port.fifa;

import com.daniel_tickets.accounts_validation.domain.user.model.User;

public interface FifaValidationPort {

    boolean isValid(User user);

}
