package com.backandwhite.core.infrastructure.bd.postgres.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void testCreateSort_null_returnsUnsorted() {
        Sort result = Util.createSort(null);
        assertTrue(result.isUnsorted());
    }

    @Test
    void testCreateSort_emptyString_returnsUnsorted() {
        Sort result = Util.createSort("   ");
        assertTrue(result.isUnsorted());
    }

    @Test
    void testCreateSort_validAsc_returnsAscSort() {
        Sort result = Util.createSort("name:asc");

        assertFalse(result.isUnsorted());
        Sort.Order order = result.getOrderFor("name");

        assertNotNull(order);
        assertEquals(Sort.Direction.ASC, order.getDirection());
    }

    @Test
    void testCreateSort_validDesc_returnsDescSort() {
        Sort result = Util.createSort("age:desc");

        assertFalse(result.isUnsorted());
        Sort.Order order = result.getOrderFor("age");

        assertNotNull(order);
        assertEquals(Sort.Direction.DESC, order.getDirection());
    }

    @Test
    void testCreateSort_withoutDirection_returnsAsc() {
        Sort result = Util.createSort("email");

        assertFalse(result.isUnsorted());
        Sort.Order order = result.getOrderFor("email");

        assertNotNull(order);
        assertEquals(Sort.Direction.ASC, order.getDirection());
    }

    @Test
    void testCreateSort_invalidFormat_returnsUnsorted() {
        Sort result = Util.createSort(":::");

        assertTrue(result.isUnsorted());
    }
}