package com.backandwhite.core.api.controllers.group;

import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import com.backandwhite.core.infrastructure.bd.postgres.repository.GroupJpaRepositoryAdapter;
import com.backandwhite.core.provider.group.GroupProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DeleteGroupControllerTestIT extends GroupProvider {

    @Autowired
    private GroupJpaRepositoryAdapter repositoryAdapter;

    @ParameterizedTest
    @MethodSource("deleteByIdOk")
    void delete_by_id(Long id, GroupEntity groupEntity) {

        repositoryAdapter.save(groupEntity);

        webTestClient.delete()
                .uri(GROUP_BASE_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("groupRecordNotFound")
    void delete_group_by_id_not_found(Long id, ErrorResponse errorResponseExpected) {

        ErrorResponse response = webTestClient.delete()
                .uri(GROUP_BASE_URL + "/{id}", id)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("timeStamp")
                .isEqualTo(errorResponseExpected);
    }
}