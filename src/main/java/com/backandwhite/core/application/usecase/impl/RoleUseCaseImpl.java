package com.backandwhite.core.application.usecase.impl;

import com.backandwhite.core.application.usecase.RoleUseCase;
import com.backandwhite.core.domain.Role;
import com.backandwhite.core.domain.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleUseCaseImpl implements RoleUseCase {

    private final RoleRepository roleRepository;

    @Override
    public Role create(Role input) {
        return roleRepository.create(input);
    }

    @Override
    public List<Role> findAll(Integer page, Integer size, String sort) {
        return roleRepository.findAll(page, size, sort);
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.getById(id);
    }

    @Override
    public Role update(Long id, Role input) {
        Role role = this.getById(id);
        BeanUtils.copyProperties(input, role, "id");
        return roleRepository.update(role);
    }

    @Override
    public void delete(Long id) {
        this.getById(id);
        roleRepository.delete(id);
    }
}
