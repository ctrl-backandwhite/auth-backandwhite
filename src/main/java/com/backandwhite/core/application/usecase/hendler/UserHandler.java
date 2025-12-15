package com.backandwhite.core.application.usecase.hendler;

import com.backandwhite.core.domain.Group;
import com.backandwhite.core.domain.Role;
import com.backandwhite.core.domain.User;
import com.backandwhite.core.domain.repository.GroupRepository;
import com.backandwhite.core.domain.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserHandler {

    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;

    public void validateUser(User user) {
        this.addRoleToUser(user);
        this.addGroupToUser(user);
    }

    private void addRoleToUser(User user) {

        if(Objects.isNull(user.getRoles()) || user.getRoles().isEmpty()){
            return;
        }

        List<Role> roles = user.getRoles().stream()
                .map( r -> roleRepository.getById(r.getId())).toList();

        user.setRoles(roles);
    }

    private void addGroupToUser(User user) {

        if(Objects.isNull(user.getGroups()) || user.getGroups().isEmpty()) {
            return;
        }

        List<Group> groups = user.getGroups().stream()
                .map( g -> groupRepository.getById(g.getId())).toList();

        user.setGroups(groups);
    }
}
