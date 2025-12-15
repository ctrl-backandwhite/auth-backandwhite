package com.backandwhite.core.api.mappers;

import com.backandwhite.core.domain.Group;
import com.backandwhite.core.domain.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserDtoMappersTest {

    private final UserDtoMappers mapper = Mappers.getMapper(UserDtoMappers.class);

    @Test
    void mapRoleIdToRole_null_returnsEmptyList() {
        List<Role> roles = mapper.mapRoleIdToRole(null);

        assertNotNull(roles);
        assertTrue(roles.isEmpty());
    }

    @Test
    void mapRoleIdToRole_empty_returnsEmptyList() {
        List<Role> roles = mapper.mapRoleIdToRole(List.of());

        assertNotNull(roles);
        assertTrue(roles.isEmpty());
    }

    @Test
    void mapRoleIdToRole_validIds_returnsRoleList() {
        List<Long> ids = List.of(1L, 2L, 3L);

        List<Role> roles = mapper.mapRoleIdToRole(ids);

        assertNotNull(roles);
        assertEquals(3, roles.size());
        assertEquals(1L, roles.get(0).getId());
        assertEquals(2L, roles.get(1).getId());
        assertEquals(3L, roles.get(2).getId());
    }

    @Test
    void mapGroupIdToGroup_null_returnsEmptyList() {
        List<Group> groups =  mapper.mapGroupIdsToGroups(null);
        assertNotNull(groups);
        assertTrue(groups.isEmpty());
    }

    @Test
    void mapGroupIdToGroup_empty_returnsEmptyList() {
        List<Group> groups =  mapper.mapGroupIdsToGroups(List.of());
        assertNotNull(groups);
        assertTrue(groups.isEmpty());
    }

    @Test
    void mapGroupIdsToGroups_is_ok() {
        List<Group> groups = mapper.mapGroupIdsToGroups(List.of(1L, 2L, 3L));

        assertNotNull(groups);
        assertEquals(1L, groups.getFirst().getId());
        assertEquals(2L, groups.get(1).getId());
        assertEquals(3L, groups.getLast().getId());
    }

}