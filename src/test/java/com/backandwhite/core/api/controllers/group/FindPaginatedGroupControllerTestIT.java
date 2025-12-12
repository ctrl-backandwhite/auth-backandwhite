package com.backandwhite.core.api.controllers.group;

import com.backandwhite.core.api.dtos.out.GroupDtoOut;
import com.backandwhite.core.infrastructure.bd.postgres.repository.GroupJpaRepositoryAdapter;
import com.backandwhite.core.provider.group.GroupProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FindPaginatedGroupControllerTestIT extends GroupProvider {

    @Autowired
    private GroupJpaRepositoryAdapter groupJpaRepositoryAdapter;

    @Test
    void get_all_groups_paginated() {
        groupJpaRepositoryAdapter.saveAll(List.of(
                        getGroupEntityOne().withId(null),
                        getGroupEntityTwo().withId(null)));

        List<GroupDtoOut> dtoOuts = webTestClient
                .get()
                .uri("/v1/groups?page=0&size=9&sort=id:asc")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(GroupDtoOut.class)
                .returnResult()
                .getResponseBody();

        assertThat(dtoOuts).usingRecursiveComparison()
                .isEqualTo(List.of(getGroupDtoOutOne(), getGroupDtoOutTwo()));
    }
}
