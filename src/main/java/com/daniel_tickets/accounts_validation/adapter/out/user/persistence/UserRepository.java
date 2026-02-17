package com.daniel_tickets.accounts_validation.adapter.out.user.persistence;

import com.daniel_tickets.accounts_validation.domain.user.type.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<JpaUserEntity, String> {

    List<JpaUserEntity> findAllByActivity(Activity activity);

}
