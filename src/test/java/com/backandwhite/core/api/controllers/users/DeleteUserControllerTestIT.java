package com.backandwhite.core.api.controllers.users;

import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import com.backandwhite.core.infrastructure.bd.postgres.repository.GroupJpaRepositoryAdapter;
import com.backandwhite.core.infrastructure.bd.postgres.repository.RoleJpaRepositoryAdapter;
import com.backandwhite.core.infrastructure.bd.postgres.repository.UserJpaRepositoryAdapter;
import com.backandwhite.core.provider.user.UserProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DeleteUserControllerTestIT extends UserProvider {

    @Autowired
    private UserJpaRepositoryAdapter userJpaRepositoryAdapter;

    @Autowired
    private RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;

    @Autowired
    private GroupJpaRepositoryAdapter groupJpaRepositoryAdapter;

    @ParameterizedTest
    @MethodSource("deleteUserProvider")
    void delete_By_Id_successfully(Long expectedId, RoleEntity roleEntity, GroupEntity groupEntity) {

        roleJpaRepositoryAdapter.save(roleEntity);
        groupJpaRepositoryAdapter.save(groupEntity);
        userJpaRepositoryAdapter.save(getUserEntityOne());

        webTestClient.delete()
                .uri(V1_USERS + "/{id}", expectedId)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("userRecordNotFound")
    void delete_user_by_id_not_found(Long expectedId, ErrorResponse expectedErrorResponse) {

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
