package com.daniel_tickets.accounts_validation.port.user;

import com.daniel_tickets.accounts_validation.domain.user.model.User;

public interface UserCommandPort {

    void update(String email, User user);

}
