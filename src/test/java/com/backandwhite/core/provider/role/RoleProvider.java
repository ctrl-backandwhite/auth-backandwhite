package com.backandwhite.core.provider.role;

import com.backandwhite.core.api.controllers.BaseIntegrationIT;
import com.backandwhite.core.api.dtos.in.RoleDtoIn;
import com.backandwhite.core.api.dtos.out.RoleDtoOut;
import com.backandwhite.core.domain.Role;
import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleProvider extends BaseIntegrationIT {

    public static final String ROLE_BASE_URL = "/v1/roles";

    public static final String DEFAULT = "DEFAULT";
    public static final String ROLE_DEFAULT = "ROLE_DEFAULT";
    public static final String DESCRIPTION_ONE = "Rol por defecto.";

    public static final String ADMIN = "ADMIN";
    public static final String ROEL_ADMIN = "ADMIN";
    public static final String DESCRIPTION_TWO = "Rol administrador.";

    /**
     * Create role
     */
    static Stream<Arguments> createRoleOk() {
        return Stream.of(
                Arguments.of(getRoleDtoInOne(), getRoleDtoOutOne()),
                Arguments.of(getRoleDtoInTwo(), getRoleDtoOutTwo().withId(1L))
        );
    }

    static Stream<Arguments> createRoleBadRequest() {
        return Stream.of(
          Arguments.of(getRoleDtoInOne().withName(null), getErrorResponse(BR_001, "name: " + IS_NOT_EMPTY)),
          Arguments.of(getRoleDtoInOne().withUniqueName(null), getErrorResponse(BR_001, "uniqueName: "+ IS_NOT_EMPTY))
        );
    }

    /**
     * Delete role by Id
     */
    static Stream<Arguments> deleteRoleByIdOk() {
        return Stream.of(
                Arguments.of(ID_ONE, getRoleEntityOne())
        );
    }

    /**
     * Get all paginated
     */
    static Stream<Arguments> getAllGroupsPaginated() {
        return Stream.of(
                Arguments.of(List.of(getRoleEntityOne(), getRoleEntityTwo()), List.of(getRoleDtoOutOne(), getRoleDtoOutTwo()))
        );
    }

    /**
     * Get by id
     */
    static Stream<Arguments> getRoleByIdOk() {
       return Stream.of(
         Arguments.of(ID_ONE, getRoleEntityOne(), getRoleDtoOutOne())
       );
    }

    /**
     * Update role
     */
    static Stream<Arguments> updateRoleOk() {
        return Stream.of(
                Arguments.of(ID_ONE, getRoleEntityOne(), getRoleDtoInTwo(), getRoleDtoOutTwo().withId(ID_ONE))
        );
    }

    public  static RoleDtoIn getRoleDtoInOne() {
        return RoleDtoIn.builder()
                .name(DEFAULT)
                .uniqueName(ROLE_DEFAULT)
                .description(DESCRIPTION_ONE)
                .isActive(IS_ACTIVE)
                .build();
    }

    public  static RoleDtoIn getRoleDtoInTwo() {
        return RoleDtoIn.builder()
                .name(ADMIN)
                .uniqueName(ROEL_ADMIN)
                .description(DESCRIPTION_TWO)
                .isActive(IS_ACTIVE)
                .build();
    }

    public  static Role getRoleOne() {
        return Role.builder()
                .id(ID_ONE)
                .name(DEFAULT)
                .uniqueName(ROLE_DEFAULT)
                .description(DESCRIPTION_ONE)
                .isActive(IS_ACTIVE)
                .build();
    }

    public  static Role getRoleTwo() {
        return Role.builder()
                .id(ID_TWO)
                .name(ADMIN)
                .uniqueName(ROEL_ADMIN)
                .description(DESCRIPTION_TWO)
                .isActive(IS_ACTIVE)
                .build();
    }

    public  static RoleDtoOut getRoleDtoOutOne() {
        return RoleDtoOut.builder()
                .id(ID_ONE)
                .name(DEFAULT)
                .uniqueName(ROLE_DEFAULT)
                .description(DESCRIPTION_ONE)
                .isActive(IS_ACTIVE)
                .build();
    }

    public  static RoleDtoOut getRoleDtoOutTwo() {
        return RoleDtoOut.builder()
                .id(ID_TWO)
                .name(ADMIN)
                .uniqueName(ROEL_ADMIN)
                .description(DESCRIPTION_TWO)
                .isActive(IS_ACTIVE)
                .build();
    }

    public  static RoleEntity getRoleEntityOne() {
        return RoleEntity.builder()
                .name(DEFAULT)
                .uniqueName(ROLE_DEFAULT)
                .description(DESCRIPTION_ONE)
                .isActive(IS_ACTIVE)
                .build();
    }

    public  static RoleEntity getRoleEntityTwo() {
        return RoleEntity.builder()
                .name(ADMIN)
                .uniqueName(ROEL_ADMIN)
                .description(DESCRIPTION_TWO)
                .isActive(IS_ACTIVE)
                .build();
    }
}
