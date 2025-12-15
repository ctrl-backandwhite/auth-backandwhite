package com.backandwhite.core.api.mappers;

import com.backandwhite.core.api.dtos.in.UserDtoIn;
import com.backandwhite.core.api.dtos.out.UserDtoOut;
import com.backandwhite.core.domain.Group;
import com.backandwhite.core.domain.Role;
import com.backandwhite.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDtoMappers {

    UserDtoOut toDtoOut(User user);

    @Mapping(target = "roles", source = "roleIds", qualifiedByName = "mapRoleIdToRole")
    @Mapping(target = "groups", source = "groupIds", qualifiedByName = "mapGroupIdsToGroups")
    User toDomain(UserDtoIn userDtoIn);

    @Named("mapRoleIdToRole")
    default List<Role> mapRoleIdToRole(List<Long> roleIds) {

        if (Objects.isNull(roleIds) || roleIds.isEmpty()){
            return Collections.emptyList();
        }
        return roleIds.stream().map(id -> Role.builder().id(id).build()).toList();
    }

    @Named("mapGroupIdsToGroups")
    default List<Group> mapGroupIdsToGroups(List<Long> groupIds) {
        if(Objects.isNull(groupIds) || groupIds.isEmpty()) {
            return Collections.emptyList();
        }

        return groupIds.stream().map( id -> Group.builder().id(id).build()).toList();
    }

    List<UserDtoOut> toDtoOutList(List<User> users);

}
