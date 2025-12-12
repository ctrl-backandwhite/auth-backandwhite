package com.backandwhite.core.api.controllers.role;

import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import com.backandwhite.core.infrastructure.bd.postgres.repository.RoleJpaRepositoryAdapter;
import com.backandwhite.core.provider.role.RoleProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DeleteRoleControllerTestIT extends RoleProvider {

    @Autowired
    private RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;

    @ParameterizedTest
    @MethodSource("deleteRoleByIdOk")
    void delete_role_by_id(Long id, RoleEntity roleEntity) {

        roleJpaRepositoryAdapter.save(roleEntity);

        webTestClient.delete()
                .uri(ROLE_BASE_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("recordNotFound")
    void delete_role_not_found(Long id, ErrorResponse errorResponseExpected) {

        ErrorResponse errorResponse = webTestClient.delete()
                .uri(ROLE_BASE_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorResponse).usingRecursiveComparison()
                .ignoringFields("timeStamp")
                .isEqualTo(errorResponseExpected);
    }
}