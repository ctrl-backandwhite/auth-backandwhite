package com.backandwhite.core.infrastructure.bd.postgres.repository.impl;

import com.backandwhite.core.domain.User;
import com.backandwhite.core.domain.exception.EntityNotFoundException;
import com.backandwhite.core.infrastructure.bd.postgres.entity.UserEntity;
import com.backandwhite.core.infrastructure.bd.postgres.mappers.UserEntityMapper;
import com.backandwhite.core.infrastructure.bd.postgres.repository.UserJpaRepositoryAdapter;
import com.backandwhite.core.provider.Provider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.backandwhite.core.domain.exception.Messages.ENTITY_NOT_FOUND;
import static com.backandwhite.core.provider.user.UserProvider.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterImplTest extends Provider {

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

    @ParameterizedTest
    @MethodSource("findAllProvider")
    void findAll_test(Integer page,
                      Integer size,
                      String sortBy,
                      int expectedPage,
                      int expectedSize,
                      Sort expectedSort) {

        Pageable expectedPageable = PageRequest.of(expectedPage, expectedSize, expectedSort);

        List<UserEntity> entityList = List.of(
                getUserEntityOne().withId(1L),
                getUserEntityOne().withId(2L)
        );

        Page<UserEntity> mockPage = new PageImpl<>(entityList);

        List<User> expectedDomainList = List.of(
                getUserOne(),
                getUserOne()
        );

        when(userJpaRepositoryAdapter.findAll(expectedPageable)).thenReturn(mockPage);
        when(userEntityMapper.toDomainList(entityList)).thenReturn(expectedDomainList);

        List<User> actualList = userRepositoryAdapterImpl.findAll(page, size, sortBy);

        assertThat(actualList)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedDomainList);

        verify(userJpaRepositoryAdapter, times(1)).findAll(expectedPageable);
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
    void getById_whenUserNotFound_shouldThrowException() {

        Long id = 1L;

        when(userJpaRepositoryAdapter.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userRepositoryAdapterImpl.getById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(
                        String.format(
                                ENTITY_NOT_FOUND.getMessage(),
                                "usuario",
                                id
                        )
                )
                .extracting("code")
                .isEqualTo(ENTITY_NOT_FOUND.getCode());

        verify(userJpaRepositoryAdapter).findById(id);
        verifyNoInteractions(userEntityMapper);
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