package com.backandwhite.core.api.controllers.role;

import com.backandwhite.core.api.dtos.out.RoleDtoOut;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import com.backandwhite.core.infrastructure.bd.postgres.repository.RoleJpaRepositoryAdapter;
import com.backandwhite.core.provider.role.RoleProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FindPaginatedRoleControllerTestIT extends RoleProvider {

    @Autowired
    private RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;

    @ParameterizedTest
    @MethodSource("getAllGroupsPaginated")
    void get_all_groups_paginated(List<RoleEntity> roleEntities, List<RoleDtoOut> dtoOutsExpected) {

        roleJpaRepositoryAdapter.saveAll(roleEntities);

        List<RoleDtoOut> dtoOuts = webTestClient
                .get()
                .uri("/v1/roles?page=0&size=9&sort=id:asc")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(RoleDtoOut.class)
                .returnResult()
                .getResponseBody();

        assertThat(dtoOuts).usingRecursiveComparison()
                .isEqualTo(dtoOutsExpected);
    }
}
