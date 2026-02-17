package com.daniel_tickets.accounts_validation.port.user;

import com.daniel_tickets.accounts_validation.domain.user.model.User;
import com.daniel_tickets.accounts_validation.domain.user.type.Activity;

import java.util.List;

public interface UserQueryPort {

    List<User> getAll(Activity activity);

}
