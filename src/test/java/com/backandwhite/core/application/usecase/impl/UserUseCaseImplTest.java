package com.backandwhite.core.application.usecase.impl;

import com.backandwhite.core.domain.User;
import com.backandwhite.core.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.backandwhite.core.provider.Provider.ID_ONE;
import static com.backandwhite.core.provider.user.UserProvider.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCaseImpl userUseCaseImpl;

    @Test
    void create_shouldDelegateToUserRepositoryCreate() {

        when(userRepository.create(any(User.class))).thenReturn(getUserOne());

        User result = userUseCaseImpl.create(getUserOne().withId(null));

        verify(userRepository, times(1)).create(any(User.class));
        assertThat(result).usingRecursiveComparison().isEqualTo(getUserOne());
    }

    @Test
    void findAll_shouldDelegateToUserRepositoryFindAll() {

        Integer page = 0;
        Integer size = 10;
        String sort = "id";
        List<User> expectedList = List.of(getUserOne(), getUserTwo());

        when(userRepository.findAll(page, size, sort)).thenReturn(expectedList);

        List<User> result = userUseCaseImpl.findAll(page, size, sort);

        verify(userRepository, times(1)).findAll(page, size, sort);
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedList);
    }

    @Test
    void getById_shouldDelegateToUserRepositoryGetById() {

        when(userRepository.getById(ID_ONE)).thenReturn(getUserOne());

        User result = userUseCaseImpl.getById(ID_ONE);

        verify(userRepository, times(1)).getById(ID_ONE);
        assertThat(result).usingRecursiveComparison().isEqualTo(getUserOne());
    }

    @Test
    void update_shouldMergePropertiesAndCallRepositoryUpdate() {
        User inputData = getUserTwo().withId(null);

        User expectedUserAfterUpdate = getUserOne().withEmail(inputData.getEmail())
                .withFirstName(inputData.getFirstName());

        when(userRepository.getById(ID_ONE)).thenReturn(getUserOne());
        when(userRepository.update(any(User.class))).thenReturn(expectedUserAfterUpdate);

        User result = userUseCaseImpl.update(ID_ONE, inputData);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedUserAfterUpdate);
    }

    @Test
    void delete_shouldVerifyExistenceAndCallRepositoryDelete() {

        User existingUser = getUserOne();
        when(userRepository.getById(ID_ONE)).thenReturn(existingUser);

        userUseCaseImpl.delete(ID_ONE);
        verify(userRepository, times(1)).getById(ID_ONE);
        verify(userRepository, times(1)).delete(ID_ONE);
    }
}