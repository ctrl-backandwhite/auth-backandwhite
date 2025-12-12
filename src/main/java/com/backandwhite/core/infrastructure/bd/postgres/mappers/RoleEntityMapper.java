package com.backandwhite.core.infrastructure.bd.postgres.mappers;

import com.backandwhite.core.domain.Role;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleEntityMapper {

    RoleEntity toEntity(Role role);

    Role toDomain(RoleEntity roleEntity);

    List<Role> toDomainList(List<RoleEntity> roleEntities);

}