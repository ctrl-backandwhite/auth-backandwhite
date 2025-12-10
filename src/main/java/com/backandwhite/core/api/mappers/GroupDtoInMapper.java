package com.backandwhite.core.api.mappers;

import com.backandwhite.core.api.dtos.in.GroupDtoIn;
import com.backandwhite.core.api.dtos.out.GroupDtoOut;
import com.backandwhite.core.domain.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupDtoInMapper {

    GroupDtoOut toDto(Group group);

    Group toDomain(GroupDtoIn groupDtoIn);

    List<GroupDtoOut> toDtoList(List<Group> groups);
}