package com.backandwhite.core.domain.utils;

import com.backandwhite.core.domain.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class JsonUtilsTest {


    private static class TestObject {
        private String name;
        private int value;

        public TestObject(String name, int value) {
            this.name = name;
            this.value = value;
        }
        public String getName() { return name; }
        public int getValue() { return value; }
    }

    @Test
    void toJson_should_return_pretty_printed_json_string() {

        TestObject inputObject = new TestObject("TestName", 123);
        String resultJson = JsonUtils.toJson(inputObject);

        assertNotNull(resultJson);

        assertThat(resultJson).contains("\"name\" : \"TestName\"");
        assertThat(resultJson).contains("\"value\" : 123");
        assertThat(resultJson).contains("\n");
    }


    @Test
    void toJson_should_throw_BadRequestException_on_serialization_error() {

        class CircularReferenceObject {
            public CircularReferenceObject self = this;

        }
        CircularReferenceObject circularObject = new CircularReferenceObject();

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            JsonUtils.toJson(circularObject);
        });

        assertThat(exception.getCode()).isEqualTo("PJ10");
        assertThat(exception.getMessage()).isEqualTo("Error converting object to JSON");
    }
}