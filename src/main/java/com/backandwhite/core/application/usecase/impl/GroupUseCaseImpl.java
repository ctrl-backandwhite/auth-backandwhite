package com.backandwhite.core.application.usecase.impl;

import com.backandwhite.core.application.usecase.GroupUseCase;
import com.backandwhite.core.domain.Group;
import com.backandwhite.core.domain.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupUseCaseImpl implements GroupUseCase {

    private final GroupRepository groupRepository;

    @Override
    public Group create(Group input) {
        return groupRepository.create(input);
    }

    @Override
    public List<Group> findAll(Integer page, Integer size, String sort) {
        return groupRepository.findAll(page, size, sort);
    }

    @Override
    public Group getById(Long id) {
        return groupRepository.getById(id);
    }

    @Override
    public Group update(Long id, Group input) {
        Group group = this.getById(id);
        BeanUtils.copyProperties(input, group, "id");
        return groupRepository.update(group);
    }

    @Override
    public void delete(Long id) {
        this.getById(id);
        groupRepository.delete(id);
    }
}
