package com.backandwhite.core.api.controllers.users;

import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.api.dtos.out.UserDtoOut;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import com.backandwhite.core.infrastructure.bd.postgres.entity.UserEntity;
import com.backandwhite.core.infrastructure.bd.postgres.repository.RoleJpaRepositoryAdapter;
import com.backandwhite.core.infrastructure.bd.postgres.repository.UserJpaRepositoryAdapter;
import com.backandwhite.core.provider.user.UserProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GetUserControllerTestIT extends UserProvider {

    @Autowired
    private UserJpaRepositoryAdapter userRepository;

    @Autowired
    private RoleJpaRepositoryAdapter roleJpaRepositoryAdapter;

    @ParameterizedTest
    @MethodSource("getUserByIdProvider")
    void get_user_by_id(Long expectedId, UserDtoOut expectedUser, UserEntity userEntity, RoleEntity roleEntities) {

        roleJpaRepositoryAdapter.save(roleEntities);
        userRepository.save(userEntity);

        UserDtoOut dtoOut = webTestClient.get()
                .uri(V1_USERS + "/{id}", expectedId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserDtoOut.class)
                .returnResult()
                .getResponseBody();

        assertThat(dtoOut)
                .usingRecursiveComparison()
                .isEqualTo(expectedUser);
    }

    @ParameterizedTest
    @MethodSource("userRecordNotFound")
    void get_user_by_id_not_foud(Long expectedId, ErrorResponse expectedErrorResponse) {

        ErrorResponse response = webTestClient
                .get()
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
