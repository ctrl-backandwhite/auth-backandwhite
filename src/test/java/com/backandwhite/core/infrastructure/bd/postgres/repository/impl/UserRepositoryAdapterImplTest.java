package com.backandwhite.core.infrastructure.bd.postgres.repository.impl;

import com.backandwhite.core.domain.User;
import com.backandwhite.core.infrastructure.bd.postgres.entity.UserEntity;
import com.backandwhite.core.infrastructure.bd.postgres.mappers.UserEntityMapper;
import com.backandwhite.core.infrastructure.bd.postgres.repository.UserJpaRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.backandwhite.core.provider.user.UserProvider.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterImplTest {

    @Mock
    private UserJpaRepositoryAdapter userJpaRepositoryAdapter;

    @Mock
    private UserEntityMapper userEntityMapper;

    @InjectMocks
    private UserRepositoryAdapterImpl userRepositoryAdapterImpl;

    @Test
    void create() {

        when(userEntityMapper.toEntity(getUserOne())).thenReturn(getUserEntityOne());
        when(userJpaRepositoryAdapter.save(getUserEntityOne())).thenReturn(getUserEntityOne().withId(1L));
        when(userEntityMapper.toDomain(getUserEntityOne().withId(1L))).thenReturn(getUserOne());

        User user = userRepositoryAdapterImpl.create(getUserOne());

        assertNotNull(getUserOne());
        assertThat(getUserOne()).usingRecursiveComparison()
                .isEqualTo(user);

        verify(userEntityMapper).toEntity(getUserOne());
        verify(userJpaRepositoryAdapter).save(getUserEntityOne());
        verify(userEntityMapper).toDomain(getUserEntityOne().withId(1L));
    }

    @Test
    void findAll() {

        final int page = 1;
        final int size = 10;
        final String sortBy = "id";

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<UserEntity> entityList = List.of(
                getUserEntityOne().withId(1L),
                getUserEntityOne().withId(2L));

        Page<UserEntity> mockPage = new PageImpl<>(entityList);

        List<User> expectedDomainList = List.of(
                getUserOne(),
                getUserOne());

        when(userJpaRepositoryAdapter.findAll(pageable)).thenReturn(mockPage);
        when(userEntityMapper.toDomainList(entityList)).thenReturn(expectedDomainList);

        List<User> actualUserList = userRepositoryAdapterImpl.findAll(page, size, sortBy);

        assertThat(actualUserList)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedDomainList);

        verify(userJpaRepositoryAdapter, times(1)).findAll(pageable);
    }

    @Test
    void getById() {
        when(userJpaRepositoryAdapter.findById(1L)).thenReturn(Optional.of(getUserEntityOne().withId(1L)));
        when(userEntityMapper.toDomain(getUserEntityOne().withId(1L))).thenReturn(getUserOne());

        User user = userRepositoryAdapterImpl.getById(1L);

        assertThat(getUserOne()).usingRecursiveComparison().isEqualTo(user);

        verify(userJpaRepositoryAdapter).findById(1L);
        verify(userEntityMapper).toDomain(getUserEntityOne().withId(1L));
    }

    @Test
    void update() {

        User expectedUser = getUserTwo();

        when(userEntityMapper.toEntity(getUserTwo().withId(1L))).thenReturn(getUserEntityTwo().withId(1L));
        when(userJpaRepositoryAdapter.save(getUserEntityTwo().withId(1L))).thenReturn(getUserEntityTwo().withId(1L));
        when(userEntityMapper.toDomain(getUserEntityTwo().withId(1L))).thenReturn(getUserTwo().withId(1L));

        User userResponse = userRepositoryAdapterImpl.create(getUserTwo().withId(1L));

        assertNotNull(expectedUser.withId(1L));
        assertThat(expectedUser.withId(1L)).usingRecursiveComparison()
                .isEqualTo(userResponse);
    }

    @Test
    void delete() {
        userRepositoryAdapterImpl.delete(1L);
        verify(userJpaRepositoryAdapter).deleteById(1L);
    }

}