package com.backandwhite.core.infrastructure.bd.postgres.repository.impl;

import com.backandwhite.core.domain.Role;
import com.backandwhite.core.domain.exception.EntityNotFoundException;
import com.backandwhite.core.domain.repository.RoleRepository;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import com.backandwhite.core.infrastructure.bd.postgres.mappers.RoleEntityMapper;
import com.backandwhite.core.infrastructure.bd.postgres.repository.RoleJpaRepositoryAdapter;
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
public class RoleRepositoryAdapterImpl implements RoleRepository {

    public final RoleEntityMapper mapper;
    public final RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;


    @Override
    public Role create(Role input) {
        RoleEntity roleEntity = mapper.toEntity(input);
        RoleEntity roleEntitySaved = roleJpaRepositoryAdapter.save(roleEntity);
        return mapper.toDomain(roleEntitySaved);
    }

    @Override
    public List<Role> findAll(Integer page, Integer size, String sort) {
        Sort sorting = Util.createSort(sort);
        Pageable pageable = PageRequest.of( Objects.nonNull(page) && page >= 0 ? page : 0,
                Objects.nonNull(size) && size > 0 ? size : 10, sorting);

        Page<RoleEntity> roleEntities = roleJpaRepositoryAdapter.findAll(pageable);
        return mapper.toDomainList(roleEntities.getContent());
    }

    @Override
    public Role getById(Long id) {
        RoleEntity roleEntity = roleJpaRepositoryAdapter.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        ENTITY_NOT_FOUND.getCode(),
                        String.format(ENTITY_NOT_FOUND.getMessage(), id)
                ));
        return mapper.toDomain(roleEntity);
    }

    @Override
    public Role update(Role input) {
        return this.create(input);
    }

    @Override
    public void delete(Long id) {
        roleJpaRepositoryAdapter.deleteById(id);
    }
}
