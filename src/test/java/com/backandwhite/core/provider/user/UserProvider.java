package com.backandwhite.core.provider.user;

import com.backandwhite.core.api.controllers.BaseIntegrationIT;
import com.backandwhite.core.api.dtos.in.UserDtoIn;
import com.backandwhite.core.api.dtos.out.UserDtoOut;
import com.backandwhite.core.domain.User;
import com.backandwhite.core.infrastructure.bd.postgres.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProvider extends BaseIntegrationIT {

    public static final String FIRST_NAME_ONE = "John";
    public static final String LAST_NAME_ONE = "Doe";
    public static final String EMAIL_ONE = "johndoe@example.com";
    public static final String PHONE_NUMBER_ONE = "+1-555-5555";
    public static final String PASSWORD_ONE = "123456789";

    public static final String FIRST_NAME_TWO = "Jane";
    public static final String LAST_NAME_TWO = "Smith";
    public static final String EMAIL_TWO = "jane@example.com";
    public static final String PHONE_NUMBER_TWO = "+34-666-7777";
    public static final String PASSWORD_TWO = "abcdef12345";

    /**
     * Create users
     */
    static Stream<Arguments> usersProviderSuccessfully() {
        return Stream.of(
                Arguments.of(getUserDtoInOne(), getUserDtoOutOne()),
                Arguments.of(getUserDtoInTwo(), getUserDtoOutTwo())
        );
    }

    static Stream<Arguments> usersProviderDadRequest() {
        return Stream.of(
                Arguments.of(getUserDtoInTwo().withFirstName(EMPTY), getErrorResponse(BR_001,"firstName: " + IS_NOT_EMPTY)),
                Arguments.of(getUserDtoInTwo().withLastName(EMPTY), getErrorResponse(BR_001,"lastName: " + IS_NOT_EMPTY)),
                Arguments.of(getUserDtoInTwo().withEmail(EMPTY), getErrorResponse(BR_001, "email: " + IS_NOT_EMPTY))
        );
    }

    /**
     * Retrieve user by id
     */
    static Stream<Arguments> getUserByIdProvider() {
        return  Stream.of(
                Arguments.of(ID_ONE, getUserDtoOutOne()),
                Arguments.of(ID_TWO, getUserDtoOutTwo())
        );
    }

    /**
     * Delete users
     */
    static Stream<Arguments> deleteUserProvider() {
        return  Stream.of(
                Arguments.of(ID_ONE)
        );
    }

    /**
     * Update users
     */
    static Stream<Arguments> updateUsers() {
        return Stream.of(
                Arguments.of(ID_ONE, getUserDtoInOne(), getUserDtoOutOne())
        );
    }

    public static UserDtoIn getUserDtoInOne() {
        return UserDtoIn.builder()
                .firstName(FIRST_NAME_ONE)
                .lastName(LAST_NAME_ONE)
                .email(EMAIL_ONE)
                .phoneNumber(PHONE_NUMBER_ONE)
                .password(PASSWORD_ONE)
                .build();
    }

    public static UserDtoOut getUserDtoOutOne() {
        return UserDtoOut.builder()
                .id(ID_ONE)
                .firstName(FIRST_NAME_ONE)
                .lastName(LAST_NAME_ONE)
                .email(EMAIL_ONE)
                .phoneNumber(PHONE_NUMBER_ONE)
                .build();
    }

    public static UserDtoIn getUserDtoInTwo() {
        return  UserDtoIn.builder()
                .firstName(FIRST_NAME_TWO)
                .lastName(LAST_NAME_TWO)
                .email(EMAIL_TWO)
                .phoneNumber(PHONE_NUMBER_TWO)
                .password(PASSWORD_TWO)
                .build();
    }

    public static UserDtoOut getUserDtoOutTwo() {
        return UserDtoOut.builder()
                .id(ID_TWO)
                .firstName(FIRST_NAME_TWO)
                .lastName(LAST_NAME_TWO)
                .email(EMAIL_TWO)
                .phoneNumber(PHONE_NUMBER_TWO)
                .build();
    }

    public static User getUserOne() {
        return User.builder()
                .id(ID_ONE)
                .firstName(FIRST_NAME_ONE)
                .lastName(LAST_NAME_ONE)
                .email(EMAIL_ONE)
                .phoneNumber(PHONE_NUMBER_ONE)
                .password(PASSWORD_ONE)
                .build();
    }

    public static User getUserTwo() {
        return User.builder()
                .id(ID_TWO)
                .firstName(FIRST_NAME_TWO)
                .lastName(LAST_NAME_TWO)
                .email(EMAIL_TWO)
                .phoneNumber(PHONE_NUMBER_TWO)
                .password(PASSWORD_TWO)
                .build();
    }

    public static UserEntity getUserEntityOne() {
        return UserEntity.builder()
                .firstName(FIRST_NAME_ONE)
                .lastName(LAST_NAME_ONE)
                .email(EMAIL_ONE)
                .phoneNumber(PHONE_NUMBER_ONE)
                .password(PASSWORD_ONE)
                .build();
    }

    public static UserEntity getUserEntityTwo() {
        return UserEntity.builder()
                .firstName(FIRST_NAME_TWO)
                .lastName(LAST_NAME_TWO)
                .email(EMAIL_TWO)
                .phoneNumber(PHONE_NUMBER_TWO)
                .password(PASSWORD_TWO)
                .build();
    }
}