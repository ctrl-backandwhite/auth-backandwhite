package com.backandwhite.core.infrastructure.bd.postgres.repository.impl;

import com.backandwhite.core.domain.Role;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import com.backandwhite.core.infrastructure.bd.postgres.mappers.RoleEntityMapper;
import com.backandwhite.core.infrastructure.bd.postgres.repository.RoleJpaRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.backandwhite.core.provider.role.RoleProvider.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryAdapterImplTest {

    @Mock
    private RoleEntityMapper mapper;
    @Mock
    private RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;
    @InjectMocks
    private RoleRepositoryAdapterImpl roleRepositoryAdapterImpl;

    @Test
    void create() {

        when(mapper.toEntity(any(Role.class))).thenReturn(getRoleEntityOne());
        when(roleJpaRepositoryAdapter.save(any(RoleEntity.class))).thenReturn(getRoleEntityOne());
        when(mapper.toDomain(any(RoleEntity.class))).thenReturn(getRoleOne());

        Role role = roleRepositoryAdapterImpl.create(getRoleOne());

        assertNotNull(role);
        assertThat(role).usingRecursiveComparison()
                .isEqualTo(getRoleOne());
    }

    @Test
    void findAll() {

        int page = 1;
        int size = 10;
        String sort = "desc";

        Sort sorting = Sort.by(Sort.Direction.ASC, sort);
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<RoleEntity> mockPage = new PageImpl<>(List.of(getRoleEntityOne(), getRoleEntityTwo()));

        when(roleJpaRepositoryAdapter.findAll(pageable)).thenReturn(mockPage);
        when(mapper.toDomainList(List.of(getRoleEntityOne(), getRoleEntityTwo()))).thenReturn(List.of(getRoleOne(), getRoleTwo()));

        List<Role> roles = roleRepositoryAdapterImpl.findAll(page, size, sort);

        assertFalse(roles.isEmpty());
        assertThat(roles).usingRecursiveComparison()
                .isEqualTo(List.of(getRoleOne(), getRoleTwo()));
    }

    @Test
    void getById() {

        when(roleJpaRepositoryAdapter.findById(1L)).thenReturn(Optional.of(getRoleEntityOne().withId(1L)));
        when(mapper.toDomain(getRoleEntityOne().withId(1L))).thenReturn(getRoleOne());

        Role role = roleRepositoryAdapterImpl.getById(1L);

        assertThat(getRoleOne()).usingRecursiveComparison().isEqualTo(role);

        verify(roleJpaRepositoryAdapter).findById(1L);
        verify(mapper).toDomain(getRoleEntityOne().withId(1L));
    }

    @Test
    void update() {

        Role expectedRole = getRoleTwo();

        when(mapper.toEntity(getRoleTwo().withId(1L))).thenReturn(getRoleEntityTwo().withId(1L));
        when(roleJpaRepositoryAdapter.save(getRoleEntityTwo().withId(1L))).thenReturn(getRoleEntityTwo().withId(1L));
        when(mapper.toDomain(getRoleEntityTwo().withId(1L))).thenReturn(getRoleTwo().withId(1L));

        Role roleResponse = roleRepositoryAdapterImpl.update(getRoleTwo().withId(1L));

        assertNotNull(expectedRole.withId(1L));
        assertThat(expectedRole.withId(1L)).usingRecursiveComparison()
                .isEqualTo(roleResponse);
    }

    @Test
    void delete() {
        roleRepositoryAdapterImpl.delete(1L);
        verify(roleJpaRepositoryAdapter).deleteById(1L);
    }
}