package com.backandwhite.core.provider.group;

import com.backandwhite.core.api.controllers.BaseIntegrationIT;
import com.backandwhite.core.api.dtos.in.GroupDtoIn;
import com.backandwhite.core.api.dtos.out.GroupDtoOut;
import com.backandwhite.core.domain.Group;
import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupProvider extends BaseIntegrationIT {

    public static final String GROUP_BASE_URL = "/v1/groups";

    public static final String GUEST = "GUEST";
    public static final String GROUP_GUEST = "GROUP_GUEST";
    public static final String DESCRIPTION_ONE = "Grupo de visitantes.";

    public static final String ADMIN = "ADMIN";
    public static final String GROUP_ADMIN = "GROUP_ADMIN";
    public static final String DESCRIPTION_TWO = "Grupo de admin.";

    /**
     * Create Group
     */
    static Stream<Arguments> createGroupSuccessfully() {
        return Stream.of(
                Arguments.of(getGroupDtoInOne(), getGroupDtoOutOne()),
                Arguments.of(getGroupDtoInTwo(), getGroupDtoOutTwo())
        );
    }

    static Stream<Arguments> createGroupBadRequest() {
        return Stream.of(
                Arguments.of(getGroupDtoInOne().withName(null), getErrorResponse(BR_001, "name: " + IS_NOT_EMPTY)),
                Arguments.of(getGroupDtoInOne().withUniqueName(null), getErrorResponse(BR_001, "uniqueName: " + IS_NOT_EMPTY))
        );
    }

    /**
     * Retrieve group by id
     */
    static Stream<Arguments> findByIdOk() {
        return Stream.of(
                Arguments.of(ID_ONE, getGroupEntityOne().withId(null), getGroupDtoOutOne())
        );
    }

    /**
     * Delete group by id
     */
    static Stream<Arguments> deleteByIdOk() {
        return Stream.of(
                Arguments.of(ID_ONE, getGroupEntityOne().withId(null))
        );
    }

    /**
     * Update group
     */
    static Stream<Arguments> updateGroupOk() {
        return Stream.of(
                Arguments.of(ID_ONE, getGroupEntityOne(), getGroupDtoOutTwo().withId(ID_ONE))
        );
    }

    public static GroupDtoIn getGroupDtoInOne() {
        return GroupDtoIn.builder()
                .name(GUEST)
                .uniqueName(GROUP_GUEST)
                .description(DESCRIPTION_ONE)
                .isActive(IS_ACTIVE)
                .build();
    }

    public static GroupDtoIn getGroupDtoInTwo() {
        return GroupDtoIn.builder()
                .name(ADMIN)
                .uniqueName(GROUP_ADMIN)
                .description(DESCRIPTION_TWO)
                .isActive(IS_ACTIVE)
                .build();
    }

    public static GroupDtoOut getGroupDtoOutOne() {
        return GroupDtoOut.builder()
                .id(ID_ONE)
                .name(GUEST)
                .uniqueName(GROUP_GUEST)
                .description(DESCRIPTION_ONE)
                .isActive(IS_ACTIVE)
                .build();
    }

    public static GroupDtoOut getGroupDtoOutTwo() {
        return GroupDtoOut.builder()
                .id(ID_TWO)
                .name(ADMIN)
                .uniqueName(GROUP_ADMIN)
                .description(DESCRIPTION_TWO)
                .isActive(IS_ACTIVE)
                .build();
    }

    public static Group getGroupOne() {
        return Group.builder()
                .id(ID_ONE)
                .name(GUEST)
                .uniqueName(GROUP_GUEST)
                .description(DESCRIPTION_ONE)
                .isActive(IS_ACTIVE)
                .build();
    }

    public static Group getGroupTwo() {
        return Group.builder()
                .id(ID_TWO)
                .name(ADMIN)
                .uniqueName(GROUP_ADMIN)
                .description(DESCRIPTION_TWO)
                .isActive(IS_ACTIVE)
                .build();
    }

    public static GroupEntity getGroupEntityOne() {
        return GroupEntity.builder()
                .id(ID_ONE)
                .name(GUEST)
                .uniqueName(GROUP_GUEST)
                .description(DESCRIPTION_ONE)
                .isActive(IS_ACTIVE)
                .build();
    }

    public static GroupEntity getGroupEntityTwo() {
        return GroupEntity.builder()
                .id(ID_TWO)
                .name(ADMIN)
                .uniqueName(GROUP_ADMIN)
                .description(DESCRIPTION_TWO)
                .isActive(IS_ACTIVE)
                .build();
    }
}
