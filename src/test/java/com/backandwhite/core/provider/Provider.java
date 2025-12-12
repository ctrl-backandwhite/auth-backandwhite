package com.backandwhite.core.provider;

import com.backandwhite.core.api.dtos.out.ErrorResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.junit.jupiter.params.provider.Arguments;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Provider {

    public static final Long ID_ONE = 1L;
    public static final Long ID_TWO = 2L;
    public static final Long ID_3000 = 3000L;
    public static final String BR_001 = "BR001";
    public static final String NF_001 = "NF001";
    public static final Boolean IS_ACTIVE = true;
    public static final String IS_NOT_EMPTY = "no debe estar vacío";
    public static final String RECORDE_NOT_FOUND_WITH_ID_3000 = "No se ha encontrado el registro con id 3000";

    /**
    * Record not found
    * */
    static Stream<Arguments> recordNotFound() {
        return Stream.of(
                Arguments.of(ID_3000, getErrorResponse(NF_001, RECORDE_NOT_FOUND_WITH_ID_3000))
        );
    }

    public static ErrorResponse getErrorResponse(String code, String message) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .details(new ArrayList<>())
                .timeStamp(ZonedDateTime.now())
                .build();
    }
}
