package com.daniel_tickets.accounts_validation.adapter.out.user.persistence;

import com.daniel_tickets.accounts_validation.adapter.out.user.mapper.UserMapper;
import com.daniel_tickets.accounts_validation.domain.user.model.User;
import com.daniel_tickets.accounts_validation.domain.user.type.Activity;
import com.daniel_tickets.accounts_validation.port.user.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserQueryAdapter implements UserQueryPort {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<User> getAll(Activity activity) {
        List<JpaUserEntity> jpaUserEntities = userRepository.findAllByActivity(activity);
        return userMapper.toUsers(jpaUserEntities);
    }

}
