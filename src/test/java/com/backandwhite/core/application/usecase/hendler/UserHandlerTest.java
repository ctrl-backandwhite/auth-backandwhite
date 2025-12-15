package com.backandwhite.core.application.usecase.hendler;

import com.backandwhite.core.domain.Group;
import com.backandwhite.core.domain.Role;
import com.backandwhite.core.domain.User;
import com.backandwhite.core.domain.repository.GroupRepository;
import com.backandwhite.core.domain.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.backandwhite.core.provider.Provider.ID_ONE;
import static com.backandwhite.core.provider.group.GroupProvider.getGroupOne;
import static com.backandwhite.core.provider.role.RoleProvider.getRoleOne;
import static com.backandwhite.core.provider.user.UserProvider.getUserOne;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private UserHandler userHandler;

    @Test
    void add_role_to_user() {

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

    @Test
    void add_group_to_user() {
        List<Group> groups = new ArrayList<>();
        groups.add(Group.builder()
                .id(ID_ONE)
                .build());

        User user = getUserOne().withGroups(groups);

        when(groupRepository.getById(any(Long.class))).thenReturn(getGroupOne());
        userHandler.validateUser(user);
    }

    @Test
    void add_user_with_groupIds_null() {

        User user = getUserOne().withGroups(null);
        userHandler.validateUser(user);

        assertNull(user.getGroups());
        verify(groupRepository, never()).getById(any(Long.class));
    }

    @Test
    void add_user_with_group_empty() {

        User user = getUserOne().withGroups(List.of());
        userHandler.validateUser(user);

        assertTrue(user.getGroups().isEmpty());
        verify(groupRepository, never()).getById(any(Long.class));
    }
}