package com.backandwhite.core.infrastructure.bd.postgres.repository.impl;

import com.backandwhite.core.domain.Group;
import com.backandwhite.core.domain.exception.EntityNotFoundException;
import com.backandwhite.core.domain.repository.GroupRepository;
import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import com.backandwhite.core.infrastructure.bd.postgres.mappers.GroupEntityMapper;
import com.backandwhite.core.infrastructure.bd.postgres.repository.GroupJpaRepositoryAdapter;
import com.backandwhite.core.infrastructure.bd.postgres.utils.Util;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.backandwhite.core.domain.exception.Messages.ENTITY_NOT_FOUND;

@Repository
@AllArgsConstructor
public class GroupRepositoryAdapterImpl implements GroupRepository {

    private final GroupEntityMapper mapper;
    private final GroupJpaRepositoryAdapter groupJpaRepositoryAdapter;

    @Override
    public Group create(Group input) {
        GroupEntity groupEntity = mapper.toEntity(input);
        GroupEntity groupEntitySaved = groupJpaRepositoryAdapter.save(groupEntity);
        return mapper.toDomain(groupEntitySaved);
    }

    @Override
    public List<Group> findAll(Integer page, Integer size, String sort) {

        Sort sorting = Util.createSort(sort);
        Pageable pageable = PageRequest.of( Objects.nonNull(page) && page >= 0 ? page : 0,
                Objects.nonNull(size) && size > 0 ? size : 10, sorting);

        Page<GroupEntity> groupEntities =  groupJpaRepositoryAdapter.findAll(pageable);
        return mapper.toDomainList(groupEntities.getContent());
    }

    @Override
    public Group getById(Long id) {
        GroupEntity groupEntity = groupJpaRepositoryAdapter.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ENTITY_NOT_FOUND.getCode(),
                        String.format(ENTITY_NOT_FOUND.getMessage(), id))
        );
        return mapper.toDomain(groupEntity);
    }

    @Override
    public Group update(Group input) {
        return this.create(input);
    }

    @Override
    public void delete(Long id) {
        groupJpaRepositoryAdapter.deleteById(id);
    }
}
