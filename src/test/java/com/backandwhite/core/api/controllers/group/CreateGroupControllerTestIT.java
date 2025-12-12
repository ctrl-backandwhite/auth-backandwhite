package com.backandwhite.core.api.controllers.group;

import com.backandwhite.core.api.dtos.in.GroupDtoIn;
import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.api.dtos.out.GroupDtoOut;
import com.backandwhite.core.provider.group.GroupProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CreateGroupControllerTestIT extends GroupProvider {

    @ParameterizedTest
    @MethodSource("createGroupSuccessfully")
    void create_users_successfully(GroupDtoIn groupDtoIn, GroupDtoOut groupDtoOutExpected) {

        GroupDtoOut dtoOut = webTestClient
                .post()
                .uri(GROUP_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(groupDtoIn)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(GroupDtoOut.class)
                .returnResult()
                .getResponseBody();

        assertThat(dtoOut).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(groupDtoOutExpected);
    }

    @ParameterizedTest
    @MethodSource("createGroupBadRequest")
    void create_group_badRequest(GroupDtoIn groupDtoIn, ErrorResponse errorExpected) {

        ErrorResponse errorResponse = webTestClient
                .post()
                .uri(GROUP_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(groupDtoIn)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorResponse).usingRecursiveComparison()
                .ignoringFields("timeStamp")
                .isEqualTo(errorExpected);
    }
}