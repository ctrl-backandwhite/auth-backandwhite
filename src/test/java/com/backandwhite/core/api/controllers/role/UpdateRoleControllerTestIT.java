package com.backandwhite.core.api.controllers.role;

import com.backandwhite.core.api.dtos.in.RoleDtoIn;
import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.api.dtos.out.RoleDtoOut;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import com.backandwhite.core.infrastructure.bd.postgres.repository.RoleJpaRepositoryAdapter;
import com.backandwhite.core.provider.role.RoleProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UpdateRoleControllerTestIT extends RoleProvider {

    @Autowired
    private RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;

    @ParameterizedTest
    @MethodSource("updateRoleOk")
    void update_role_ok(Long id, RoleEntity roleEntity, RoleDtoIn roleDtoIn, RoleDtoOut dtoOutExpected) {

        roleJpaRepositoryAdapter.save(roleEntity);

        RoleDtoOut dtoOut = webTestClient.put()
                .uri(ROLE_BASE_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(roleDtoIn)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isOk()
                .expectBody(RoleDtoOut.class)
                .returnResult()
                .getResponseBody();

        assertThat(dtoOut).usingRecursiveComparison()
                .isEqualTo(dtoOutExpected);
    }

    @ParameterizedTest
    @MethodSource("recordNotFound")
    void update_role_ok(Long id, ErrorResponse errorResponseExpected) {

        ErrorResponse errorResponse = webTestClient.put()
                .uri(ROLE_BASE_URL + "/{id}", id)
                .bodyValue(getRoleDtoInOne())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorResponse).usingRecursiveComparison()
                .ignoringFields("timeStamp")
                .isEqualTo(errorResponseExpected);
    }
}
