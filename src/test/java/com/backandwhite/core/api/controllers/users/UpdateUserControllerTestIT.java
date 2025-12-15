package com.backandwhite.core.api.controllers.users;

import com.backandwhite.core.api.dtos.in.UserDtoIn;
import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.api.dtos.out.UserDtoOut;
import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import com.backandwhite.core.infrastructure.bd.postgres.repository.GroupJpaRepositoryAdapter;
import com.backandwhite.core.infrastructure.bd.postgres.repository.RoleJpaRepositoryAdapter;
import com.backandwhite.core.infrastructure.bd.postgres.repository.UserJpaRepositoryAdapter;
import com.backandwhite.core.provider.user.UserProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UpdateUserControllerTestIT extends UserProvider {

    @Autowired
    private UserJpaRepositoryAdapter userJpaRepositoryAdapter;

    @Autowired
    private RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;

    @Autowired
    private GroupJpaRepositoryAdapter groupJpaRepositoryAdapter;

    @ParameterizedTest
    @MethodSource("updateUsers")
    void update_user(Long id, UserDtoIn userDtoIn, UserDtoOut userDtoOutExpected, RoleEntity roleEntity, GroupEntity groupEntity) {

        groupJpaRepositoryAdapter.save(groupEntity);
        roleJpaRepositoryAdapter.save(roleEntity);
        userJpaRepositoryAdapter.save(getUserEntityOne());

        UserDtoOut userDtoOutResponse = webTestClient.put()
                .uri(V1_USERS + "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDtoIn)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserDtoOut.class)
                .returnResult()
                .getResponseBody();

        assertThat(userDtoOutResponse)
                .usingRecursiveComparison()
                .ignoringFields(
                        "password",
                        "roles.id",
                        "groups.id")
                .isEqualTo(userDtoOutExpected);
    }

    @ParameterizedTest
    @MethodSource("userRecordNotFound")
    void update_user_bad_request(Long id, ErrorResponse errorResponseExpected) {
        ErrorResponse errorResponse = webTestClient.put()
                .uri(V1_USERS + "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(getUserDtoInOne())
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorResponse)
                .usingRecursiveComparison()
                .ignoringFields("timeStamp")
                .isEqualTo(errorResponseExpected);
    }
}
