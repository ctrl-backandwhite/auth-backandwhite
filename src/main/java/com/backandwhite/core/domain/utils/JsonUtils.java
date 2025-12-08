package com.backandwhite.core.domain.utils;

import com.backandwhite.core.domain.exception.BadRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {


    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("PJ10","Error converting object to JSON");
        }
    }
}
