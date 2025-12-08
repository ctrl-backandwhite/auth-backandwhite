package com.backandwhite.core.application.usecase.impl;

import com.backandwhite.core.application.usecase.UserUseCase;
import com.backandwhite.core.domain.User;
import com.backandwhite.core.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository userRepository;

    @Override
    public User create(User input) {
        return userRepository.create(input);
    }

    @Override
    public List<User> findAll(Integer page, Integer size, String sort) {
        return userRepository.findAll(page, size, sort);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User update(Long id, User input) {
        User user = this.getById(id);
        BeanUtils.copyProperties(input, user);
        user.setId(id);
        return userRepository.update(user);
    }

    @Override
    public void delete(Long id) {
        this.getById(id);
        userRepository.delete(id);
    }
}
