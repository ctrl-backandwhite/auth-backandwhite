package com.backandwhite.core.application.usecase.hendler;

import com.backandwhite.core.domain.Role;
import com.backandwhite.core.domain.User;
import com.backandwhite.core.domain.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.backandwhite.core.provider.Provider.ID_ONE;
import static com.backandwhite.core.provider.role.RoleProvider.getRoleOne;
import static com.backandwhite.core.provider.user.UserProvider.getUserOne;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserHandler userHandler;

    @Test
    void validateUserTest() {

        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder()
                .id(ID_ONE)
                .build());

        User  user = getUserOne().withRoles(roles);

        when(roleRepository.getById(any(Long.class))).thenReturn(getRoleOne());
        userHandler.validateUser(user);

        assertFalse(user.getRoles().isEmpty());
    }

    @Test
    void validate_user_with_roles_empty() {

        User  user = getUserOne().withRoles(List.of());

        userHandler.validateUser(user);
        assertTrue(user.getRoles().isEmpty(), "Roles should remain empty");
        verify(roleRepository, never()).getById(any());
    }

    @Test
    void validate_user_with_roles_null() {

        User  user = getUserOne().withRoles(null);

        userHandler.validateUser(user);
        assertNull(user.getRoles(), "Roles should remain null");
        verify(roleRepository, never()).getById(any());
    }
}