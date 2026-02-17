package com.daniel_tickets.accounts_validation.adapter.out.user.persistence;

import com.daniel_tickets.accounts_validation.domain.user.type.Activity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class JpaUserEntity {

    @Id
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Activity activity;

    @CreatedDate
    private LocalDate validationDate;

}
