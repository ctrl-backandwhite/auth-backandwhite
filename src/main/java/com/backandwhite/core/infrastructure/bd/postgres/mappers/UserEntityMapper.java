package com.backandwhite.core.infrastructure.bd.postgres.mappers;

import com.backandwhite.core.domain.User;
import com.backandwhite.core.infrastructure.bd.postgres.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {

    UserEntity toEntity(User user);

    User toDomain(UserEntity userEntity);

    List<User> toDomainList(List<UserEntity> userEntities);

}
