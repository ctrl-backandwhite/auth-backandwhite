package com.backandwhite.core.api.controllers.group;

import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.api.dtos.out.GroupDtoOut;
import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import com.backandwhite.core.infrastructure.bd.postgres.repository.GroupJpaRepositoryAdapter;
import com.backandwhite.core.provider.group.GroupProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GetGroupControllerTestIT extends GroupProvider {

    @Autowired
    private GroupJpaRepositoryAdapter groupRepository;

    @ParameterizedTest
    @MethodSource("findByIdOk")
    void get_group_ok(Long id, GroupEntity groupEntity,  GroupDtoOut expectedGroup) {

        groupRepository.save(groupEntity);

        GroupDtoOut dtoOut = webTestClient
                .get()
                .uri(GROUP_BASE_URL + "/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GroupDtoOut.class)
                .returnResult()
                .getResponseBody();

        assertThat(dtoOut).usingRecursiveComparison()
                .isEqualTo(expectedGroup);
    }

    @ParameterizedTest
    @MethodSource("groupRecordNotFound")
    void get_group_not_found(Long id,  ErrorResponse expectedError) {

        ErrorResponse errorResponse = webTestClient
                .get()
                .uri(GROUP_BASE_URL + "/{id}", id)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorResponse).usingRecursiveComparison()
                .ignoringFields("timeStamp")
                .isEqualTo(expectedError);
    }
}
