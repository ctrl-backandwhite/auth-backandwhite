package com.backandwhite.core.application.usecase.impl;

import com.backandwhite.core.domain.Group;
import com.backandwhite.core.domain.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.backandwhite.core.provider.Provider.ID_ONE;
import static com.backandwhite.core.provider.group.GroupProvider.getGroupOne;
import static com.backandwhite.core.provider.group.GroupProvider.getGroupTwo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GroupUseCaseImplTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupUseCaseImpl groupUseCaseImpl;

    @Test
    void create_shouldDelegateToGroupRepositoryCreate() {

        when(groupRepository.create(any(Group.class))).thenReturn(getGroupOne());

        Group result = groupUseCaseImpl.create(getGroupOne().withId(null));

        verify(groupRepository, times(1)).create(any(Group.class));
        assertThat(result).usingRecursiveComparison().isEqualTo(getGroupOne());
    }

    @Test
    void findAll_shouldDelegateToGroupRepositoryFindAll() {

        Integer page = 0;
        Integer size = 10;
        String sort = "id";
        List<Group> expectedList = List.of(getGroupOne(), getGroupTwo());

        when(groupRepository.findAll(page, size, sort)).thenReturn(expectedList);

        List<Group> result = groupUseCaseImpl.findAll(page, size, sort);

        verify(groupRepository, times(1)).findAll(page, size, sort);
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedList);
    }

    @Test
    void getById_shouldDelegateToGroupRepositoryGetById() {

        when(groupRepository.getById(ID_ONE)).thenReturn(getGroupOne());

        Group result = groupUseCaseImpl.getById(ID_ONE);

        verify(groupRepository, times(1)).getById(ID_ONE);
        assertThat(result).usingRecursiveComparison().isEqualTo(getGroupOne());
    }

    @Test
    void update_shouldMergePropertiesAndCallRepositoryUpdate() {
        Group inputData = getGroupTwo().withId(null);

        Group expectedGroupAfterUpdate = getGroupOne().withName(inputData.getName())
                .withDescription(inputData.getDescription());

        when(groupRepository.getById(ID_ONE)).thenReturn(getGroupOne());
        when(groupRepository.update(any(Group.class))).thenReturn(expectedGroupAfterUpdate);

        Group result = groupUseCaseImpl.update(ID_ONE, inputData);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedGroupAfterUpdate);
    }

    @Test
    void delete_shouldVerifyExistenceAndCallRepositoryDelete() {

        Group existingGroup = getGroupOne();
        when(groupRepository.getById(ID_ONE)).thenReturn(existingGroup);

        groupUseCaseImpl.delete(ID_ONE);
        verify(groupRepository, times(1)).getById(ID_ONE);
        verify(groupRepository, times(1)).delete(ID_ONE);
    }

}