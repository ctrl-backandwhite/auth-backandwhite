package com.backandwhite.core.application.usecase.hendler;

import com.backandwhite.core.domain.Role;
import com.backandwhite.core.domain.User;
import com.backandwhite.core.domain.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserHandler {

    private final RoleRepository roleRepository;

    public void validateUser(User user) {
        this.addRoleToUser(user);
    }

    private void addRoleToUser(User user) {

        if(Objects.isNull(user.getRoles()) || user.getRoles().isEmpty()){
            return;
        }

        List<Role> roles = user.getRoles().stream()
                .map( r -> roleRepository.getById(r.getId())).toList();

        user.setRoles(roles);
        System.out.println(user);
    }
}
