package com.backandwhite.core.api.controllers.users;

import com.backandwhite.core.api.dtos.in.UserDtoIn;
import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.api.dtos.out.UserDtoOut;
import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import com.backandwhite.core.infrastructure.bd.postgres.repository.GroupJpaRepositoryAdapter;
import com.backandwhite.core.infrastructure.bd.postgres.repository.RoleJpaRepositoryAdapter;
import com.backandwhite.core.provider.user.UserProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

class CreateUserControllerTestIT extends UserProvider {

    @Autowired
    private RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;

    @Autowired
    private GroupJpaRepositoryAdapter groupJpaRepositoryAdapter;

    @ParameterizedTest
    @MethodSource("usersProviderSuccessfully")
    void create_users_successfully(UserDtoIn in, UserDtoOut expected, RoleEntity roleEntity, GroupEntity groupEntity) {

        groupJpaRepositoryAdapter.save(groupEntity);
        roleJpaRepositoryAdapter.save(roleEntity);

        UserDtoOut response =
                webTestClient.post()
                        .uri(V1_USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(in)
                        .exchange()
                        .expectStatus().isCreated()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .expectBody(UserDtoOut.class)
                        .returnResult()
                        .getResponseBody();

        Assertions.assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("usersProviderDadRequest")
    void create_users_successfully(UserDtoIn in, ErrorResponse expected) {
        ErrorResponse response =
                webTestClient.post()
                        .uri(V1_USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(in)
                        .exchange()
                        .expectStatus().isBadRequest()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .expectBody(ErrorResponse.class)
                        .returnResult()
                        .getResponseBody();

        Assertions.assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("timeStamp")
                .isEqualTo(expected);
    }
}