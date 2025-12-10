package com.backandwhite.core.infrastructure.bd.postgres.utils;

import org.springframework.data.domain.Sort;

public class Util {

    public static Sort createSort(String sortString) {
        if (sortString == null || sortString.trim().isEmpty()) {
            return Sort.unsorted();
        }

        try {
            String[] parts = sortString.split(":");
            String property = parts[0];

            Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            return Sort.by(direction, property);
        } catch (Exception e) {
            return Sort.unsorted();
        }
    }
}
