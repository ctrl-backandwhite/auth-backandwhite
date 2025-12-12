package com.backandwhite.core.infrastructure.bd.postgres.mappers;

import com.backandwhite.core.domain.Group;
import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupEntityMapper {

    GroupEntity toEntity(Group group);

    Group toDomain(GroupEntity groupEntity);

    List<Group> toDomainList(List<GroupEntity> groups);
}