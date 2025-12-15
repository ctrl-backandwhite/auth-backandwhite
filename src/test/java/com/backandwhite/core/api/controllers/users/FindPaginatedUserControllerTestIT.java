package com.backandwhite.core.api.controllers.users;

import com.backandwhite.core.api.dtos.out.UserDtoOut;
import com.backandwhite.core.infrastructure.bd.postgres.repository.GroupJpaRepositoryAdapter;
import com.backandwhite.core.infrastructure.bd.postgres.repository.RoleJpaRepositoryAdapter;
import com.backandwhite.core.infrastructure.bd.postgres.repository.UserJpaRepositoryAdapter;
import com.backandwhite.core.provider.user.UserProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static com.backandwhite.core.provider.group.GroupProvider.getGroupEntityOne;
import static com.backandwhite.core.provider.group.GroupProvider.getGroupEntityTwo;
import static com.backandwhite.core.provider.role.RoleProvider.getRoleEntityOne;
import static com.backandwhite.core.provider.role.RoleProvider.getRoleEntityTwo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FindPaginatedUserControllerTestIT extends UserProvider {

    @Autowired
    private RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;

    @Autowired
    private UserJpaRepositoryAdapter userJpaRepositoryAdapter;

    @Autowired
    private GroupJpaRepositoryAdapter groupJpaRepositoryAdapter;

    @Test
    void get_all_users_paginated() {

        groupJpaRepositoryAdapter.saveAll(List.of(getGroupEntityOne().withId(null), getGroupEntityTwo().withId(null)));
        roleJpaRepositoryAdapter.saveAll(List.of(getRoleEntityOne().withId(null), getRoleEntityTwo().withId(null)));
        userJpaRepositoryAdapter.saveAll(List.of(getUserEntityOne(), getUserEntityTwo()));

        List<UserDtoOut> dtoOuts = webTestClient
                .get()
                .uri("/v1/users?page=0&size=9&sort=id:asc")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(UserDtoOut.class)
                .returnResult()
                .getResponseBody();

        assertThat(dtoOuts).usingRecursiveComparison()
                .isEqualTo(List.of(getUserDtoOutOne(), getUserDtoOutTwo()));
    }
}
