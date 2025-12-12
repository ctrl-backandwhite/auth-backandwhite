package com.backandwhite.core.application.usecase.impl;

import com.backandwhite.core.domain.Role;
import com.backandwhite.core.domain.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.backandwhite.core.provider.Provider.ID_ONE;
import static com.backandwhite.core.provider.role.RoleProvider.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleUseCaseImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleUseCaseImpl roleUseCaseImpl;

    @Test
    void create_shouldDelegateToRoleRepositoryCreate() {

        when(roleRepository.create(any(Role.class))).thenReturn(getRoleOne());

        Role result = roleUseCaseImpl.create(getRoleOne().withId(null));

        verify(roleRepository, times(1)).create(any(Role.class));
        assertThat(result).usingRecursiveComparison().isEqualTo(getRoleOne());
    }

    @Test
    void findAll_shouldDelegateToRoleRepositoryFindAll() {

        Integer page = 0;
        Integer size = 10;
        String sort = "id";
        List<Role> expectedList = List.of(getRoleOne(), getRoleTwo());

        when(roleRepository.findAll(page, size, sort)).thenReturn(expectedList);

        List<Role> result = roleUseCaseImpl.findAll(page, size, sort);

        verify(roleRepository, times(1)).findAll(page, size, sort);
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedList);
    }

    @Test
    void getById_shouldDelegateToRoleRepositoryGetById() {

        when(roleRepository.getById(ID_ONE)).thenReturn(getRoleOne());

        Role result = roleUseCaseImpl.getById(ID_ONE);

        verify(roleRepository, times(1)).getById(ID_ONE);
        assertThat(result).usingRecursiveComparison().isEqualTo(getRoleOne());
    }

    @Test
    void update_shouldMergePropertiesAndCallRepositoryUpdate() {
        Role inputData = getRoleTwo().withId(null);

        Role expectedRoleAfterUpdate = getRoleOne().withName(inputData.getName())
                .withDescription(inputData.getDescription());

        when(roleRepository.getById(ID_ONE)).thenReturn(getRoleOne());
        when(roleRepository.update(any(Role.class))).thenReturn(expectedRoleAfterUpdate);

        Role result = roleUseCaseImpl.update(ID_ONE, inputData);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedRoleAfterUpdate);
    }

    @Test
    void delete_shouldVerifyExistenceAndCallRepositoryDelete() {

        Role existingRole = getRoleOne();
        when(roleRepository.getById(ID_ONE)).thenReturn(existingRole);

        roleUseCaseImpl.delete(ID_ONE);
        verify(roleRepository, times(1)).getById(ID_ONE);
        verify(roleRepository, times(1)).delete(ID_ONE);
    }

}