package com.backandwhite.core.infrastructure.bd.postgres.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    static Stream<Arguments> sortProvider() {
        return Stream.of(
                Arguments.of(null, true, null, null),
                Arguments.of("   ", true, null, null),
                Arguments.of("name:asc", false, "name", Sort.Direction.ASC),
                Arguments.of("age:desc", false, "age", Sort.Direction.DESC),
                Arguments.of("email", false, "email", Sort.Direction.ASC),
                Arguments.of(":::", true, null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("sortProvider")
    void testCreateSort(String input, boolean expectedUnsorted, String expectedProperty, Sort.Direction expectedDirection) {

        Sort result = Util.createSort(input);

        if (expectedUnsorted) {
            assertTrue(result.isUnsorted());
        } else {
            assertFalse(result.isUnsorted());
            Sort.Order order = result.getOrderFor(expectedProperty);

            assertNotNull(order);
            assertEquals(expectedDirection, order.getDirection());
        }
    }
}