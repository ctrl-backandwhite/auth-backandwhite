package com.backandwhite.core.infrastructure.bd.postgres.repository.impl;

import com.backandwhite.core.domain.Group;
import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import com.backandwhite.core.infrastructure.bd.postgres.mappers.GroupEntityMapper;
import com.backandwhite.core.infrastructure.bd.postgres.repository.GroupJpaRepositoryAdapter;
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

import static com.backandwhite.core.provider.group.GroupProvider.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupRepositoryAdapterImplTest extends Provider {

    @Mock
    private GroupEntityMapper mapper;
    @Mock
    private GroupJpaRepositoryAdapter groupJpaRepositoryAdapter;
    @InjectMocks
    private GroupRepositoryAdapterImpl groupRepositoryAdapterImpl;

    @Test
    void create() {
        when(mapper.toEntity(any(Group.class))).thenReturn(getGroupEntityOne());
        when(groupJpaRepositoryAdapter.save(any(GroupEntity.class))).thenReturn(getGroupEntityOne());
        when(mapper.toDomain(any(GroupEntity.class))).thenReturn(getGroupOne());

        Group group = groupRepositoryAdapterImpl.create(getGroupOne());

        assertNotNull(group);
        assertThat(group).usingRecursiveComparison()
                .isEqualTo(getGroupOne());
    }

    @ParameterizedTest
    @MethodSource("findAllProvider")
    void findAll_test(Integer page, Integer size, String sort, int expectedPage, int expectedSize, Sort expectedSort) {

        Pageable expectedPageable = PageRequest.of(expectedPage, expectedSize, expectedSort);

        Page<GroupEntity> mockPage = new PageImpl<>(List.of(getGroupEntityOne(), getGroupEntityTwo()));

        when(groupJpaRepositoryAdapter.findAll(expectedPageable)).thenReturn(mockPage);
        when(mapper.toDomainList(List.of(getGroupEntityOne(), getGroupEntityTwo())))
                .thenReturn(List.of(getGroupOne(), getGroupTwo()));

        List<Group> result = groupRepositoryAdapterImpl.findAll(page, size, sort);

        assertFalse(result.isEmpty());
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(List.of(getGroupOne(), getGroupTwo()));
    }

    @Test
    void getById() {

        when(groupJpaRepositoryAdapter.findById(1L)).thenReturn(Optional.of(getGroupEntityOne()));
        when(mapper.toDomain(getGroupEntityOne())).thenReturn(getGroupOne());

        Group group = groupRepositoryAdapterImpl.getById(1L);

        assertThat(getGroupOne()).usingRecursiveComparison().isEqualTo(group);

        verify(groupJpaRepositoryAdapter).findById(1L);
        verify(mapper).toDomain(getGroupEntityOne());
    }

    @Test
    void update() {

        Group expectedGroup = getGroupTwo();

        when(mapper.toEntity(getGroupTwo().withId(1L))).thenReturn(getGroupEntityTwo());
        when(groupJpaRepositoryAdapter.save(getGroupEntityTwo())).thenReturn(getGroupEntityTwo());
        when(mapper.toDomain(getGroupEntityTwo())).thenReturn(getGroupTwo().withId(1L));

        Group groupResponse = groupRepositoryAdapterImpl.update(getGroupTwo().withId(1L));

        assertNotNull(expectedGroup.withId(1L));
        assertThat(expectedGroup.withId(1L)).usingRecursiveComparison()
                .isEqualTo(groupResponse);
    }

    @Test
    void delete() {
        groupRepositoryAdapterImpl.delete(1L);
        verify(groupJpaRepositoryAdapter).deleteById(1L);
    }
}