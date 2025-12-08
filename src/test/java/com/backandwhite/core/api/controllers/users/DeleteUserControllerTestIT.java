package com.backandwhite.core.api.controllers.users;

import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.infrastructure.bd.postgres.repository.UserJpaRepositoryAdapter;
import com.backandwhite.core.provider.user.UserProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DeleteUserControllerTestIT extends UserProvider {

    @Autowired
    private UserJpaRepositoryAdapter userJpaRepositoryAdapter;

    @ParameterizedTest
    @MethodSource("deleteUserProvider")
    void delete_By_Id_successfully(Long expectedId) {

        userJpaRepositoryAdapter.save(getUserEntityOne());

        webTestClient.delete()
                .uri(V1_USERS + "/{id}", expectedId)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("deleteUserNotFoundProvider")
    void delete_by_id_not_found(Long expectedId, ErrorResponse expectedErrorResponse) {

        ErrorResponse response = webTestClient.delete()
                .uri(V1_USERS + "/{id}", expectedId)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("timeStamp")
                .isEqualTo(expectedErrorResponse);
    }
}
