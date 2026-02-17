package com.daniel_tickets.accounts_validation.adapter.out.user.mapper;

import com.daniel_tickets.accounts_validation.adapter.out.user.persistence.JpaUserEntity;
import com.daniel_tickets.accounts_validation.domain.user.model.User;
import com.daniel_tickets.accounts_validation.infrastructure.mapper.config.MapStructConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {

    JpaUserEntity toEntity(User user);

    List<User> toUsers(List<JpaUserEntity> entities);

}
