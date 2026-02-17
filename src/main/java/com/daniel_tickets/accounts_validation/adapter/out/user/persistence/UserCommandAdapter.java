package com.daniel_tickets.accounts_validation.adapter.out.user.persistence;

import com.daniel_tickets.accounts_validation.adapter.out.user.exception.UserNotFoundException;
import com.daniel_tickets.accounts_validation.adapter.out.user.mapper.UserMapper;
import com.daniel_tickets.accounts_validation.domain.user.model.User;
import com.daniel_tickets.accounts_validation.port.user.UserCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCommandAdapter implements UserCommandPort {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Override
    public void update(String email, User user) {
        userRepository.findById(email)
                .orElseThrow(() -> new UserNotFoundException("User not found %s.".formatted(email)));

        JpaUserEntity jpaUserEntity = userMapper.toEntity(user);
        jpaUserEntity.setEmail(email);

        userRepository.save(jpaUserEntity);
    }
}
