package com.daniel_tickets.accounts_validation.domain.user.model;

import com.daniel_tickets.accounts_validation.domain.user.type.Activity;
import lombok.Builder;
import lombok.With;

@Builder
public record User(
        @With
        String email,
        String password,
        Activity activity) {
}
