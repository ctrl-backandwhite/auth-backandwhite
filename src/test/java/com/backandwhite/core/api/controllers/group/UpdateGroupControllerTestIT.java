package com.backandwhite.core.api.controllers.group;

import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.api.dtos.out.GroupDtoOut;
import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import com.backandwhite.core.infrastructure.bd.postgres.repository.GroupJpaRepositoryAdapter;
import com.backandwhite.core.provider.group.GroupProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UpdateGroupControllerTestIT extends GroupProvider {

    @Autowired
    private GroupJpaRepositoryAdapter groupJpaRepositoryAdapter;

    @ParameterizedTest
    @MethodSource("updateGroupOk")
    void update_group_ok(Long id, GroupEntity groupEntity, GroupDtoOut groupDtoOut) {

            groupJpaRepositoryAdapter.save(groupEntity.withId(null));

        GroupDtoOut dtoOut = webTestClient.put()
                .uri(GROUP_BASE_URL + "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(getGroupDtoInTwo())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(GroupDtoOut.class)
                .returnResult()
                .getResponseBody();

        assertThat(dtoOut).usingRecursiveComparison()
                .isEqualTo(groupDtoOut);
    }

    @ParameterizedTest
    @MethodSource("groupRecordNotFound")
    void delete_group_by_id(Long id, ErrorResponse errorResponseExpected) {

        ErrorResponse errorResponse = webTestClient.delete()
                .uri(GROUP_BASE_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorResponse).usingRecursiveComparison()
                .ignoringFields("timeStamp")
                .isEqualTo(errorResponseExpected);
    }
}
