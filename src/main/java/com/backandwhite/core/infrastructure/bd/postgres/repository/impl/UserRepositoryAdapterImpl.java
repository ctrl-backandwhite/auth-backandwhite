package com.backandwhite.core.infrastructure.bd.postgres.repository.impl;

import com.backandwhite.core.domain.User;
import com.backandwhite.core.domain.exception.EntityNotFoundException;
import com.backandwhite.core.domain.repository.UserRepository;
import com.backandwhite.core.infrastructure.bd.postgres.entity.UserEntity;
import com.backandwhite.core.infrastructure.bd.postgres.mappers.UserEntityMapper;
import com.backandwhite.core.infrastructure.bd.postgres.repository.UserJpaRepositoryAdapter;
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
public class UserRepositoryAdapterImpl implements UserRepository {

    private final UserEntityMapper mapper;
    private final UserJpaRepositoryAdapter userJpaRepositoryAdapter;

    @Override
    public User create(User input) {
        UserEntity userEntityToSave = mapper.toEntity(input);
        UserEntity userEntity = userJpaRepositoryAdapter.save(userEntityToSave);
        return mapper.toDomain(userEntity);
    }

    @Override
    public List<User> findAll(Integer page, Integer size, String sort) {

        Sort sorting = createSort(sort);
        Pageable pageable = PageRequest.of( Objects.nonNull(page) && page >= 0 ? page : 0,
                Objects.nonNull(size) && size > 0 ? size : 10, sorting);

        Page<UserEntity> userPage = userJpaRepositoryAdapter.findAll(pageable);
        return mapper.toDomainList(userPage.getContent());
    }

    @Override
    public User getById(Long id) {
        UserEntity userEntity = userJpaRepositoryAdapter.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        ENTITY_NOT_FOUND.getCode(),
                        String.format(ENTITY_NOT_FOUND.getMessage(), id)));
        return mapper.toDomain(userEntity);
    }

    @Override
    public User update(User input) {
        return this.create(input);
    }

    @Override
    public void delete(Long id) {
        userJpaRepositoryAdapter.deleteById(id);
    }


    private Sort createSort(String sortString) {
        if (sortString == null || sortString.trim().isEmpty()) {
            return Sort.unsorted();
        }

        try {
            String[] parts = sortString.split(":");
            String property = parts[0];

            Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            return Sort.by(direction, property);
        } catch (Exception e) {
            return Sort.unsorted();
        }
    }
}
