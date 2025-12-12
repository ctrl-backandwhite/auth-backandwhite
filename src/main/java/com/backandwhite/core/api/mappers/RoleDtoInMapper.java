package com.backandwhite.core.api.mappers;

import com.backandwhite.core.api.dtos.in.RoleDtoIn;
import com.backandwhite.core.api.dtos.out.RoleDtoOut;
import com.backandwhite.core.domain.Role;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleDtoInMapper {

    RoleDtoOut toDto(Role role);

    Role toDomain(RoleDtoIn roleDtoIn);

    List<RoleDtoOut> toDtoList(List<Role> roles);
}