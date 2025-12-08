package com.backandwhite.core.infrastructure.bd.postgres.mappers;

import com.backandwhite.core.domain.User;
import com.backandwhite.core.infrastructure.bd.postgres.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserEntityMapper {

    UserEntityMapper USER_ENTITY_MAPPER = Mappers.getMapper(UserEntityMapper.class);

    UserEntity toEntity(User user);

    User toDomain(UserEntity userEntity);

    List<User> toDomainList(List<UserEntity> userEntities);

}
