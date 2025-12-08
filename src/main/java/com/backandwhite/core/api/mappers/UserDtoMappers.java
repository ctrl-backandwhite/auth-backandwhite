package com.backandwhite.core.api.mappers;

import com.backandwhite.core.api.dtos.in.UserDtoIn;
import com.backandwhite.core.api.dtos.out.UserDtoOut;
import com.backandwhite.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserDtoMappers {

    UserDtoMappers USER_DTO_MAPPERS = Mappers.getMapper(UserDtoMappers.class);

    UserDtoOut toDtoOut(User user);

    User toDomain(UserDtoIn userDtoIn);

    List<UserDtoOut> toDtoOutList(List<User> users);

}
