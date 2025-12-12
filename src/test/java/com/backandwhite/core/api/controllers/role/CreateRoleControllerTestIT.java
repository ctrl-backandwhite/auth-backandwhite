package com.backandwhite.core.api.controllers.role;

import com.backandwhite.core.api.dtos.in.RoleDtoIn;
import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.api.dtos.out.RoleDtoOut;
import com.backandwhite.core.provider.role.RoleProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CreateRoleControllerTestIT extends RoleProvider {

    @ParameterizedTest
    @MethodSource("createRoleOk")
    void create_role_ok(RoleDtoIn roleDtoIn, RoleDtoOut dtoOutExpected) {

        RoleDtoOut dtoOut = webTestClient
                .post()
                .uri(ROLE_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(roleDtoIn)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isCreated()
                .expectBody(RoleDtoOut.class)
                .returnResult()
                .getResponseBody();

        assertThat(dtoOut).usingRecursiveComparison()
                .isEqualTo(dtoOutExpected);
    }

    @ParameterizedTest
    @MethodSource("createRoleBadRequest")
    void create_role_bad_request(RoleDtoIn roleDtoIn, ErrorResponse errorResponseExpected) {

        ErrorResponse errorResponse = webTestClient
                .post()
                .uri(ROLE_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(roleDtoIn)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorResponse).usingRecursiveComparison()
                .ignoringFields("timeStamp")
                .isEqualTo(errorResponseExpected);
    }
}