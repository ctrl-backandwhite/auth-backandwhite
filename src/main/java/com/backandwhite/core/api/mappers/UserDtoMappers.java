package com.backandwhite.core.api.mappers;

import com.backandwhite.core.api.dtos.in.UserDtoIn;
import com.backandwhite.core.api.dtos.out.UserDtoOut;
import com.backandwhite.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDtoMappers {

    UserDtoOut toDtoOut(User user);

    User toDomain(UserDtoIn userDtoIn);

    List<UserDtoOut> toDtoOutList(List<User> users);

}
