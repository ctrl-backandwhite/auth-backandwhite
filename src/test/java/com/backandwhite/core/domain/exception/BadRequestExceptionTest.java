package com.backandwhite.core.domain.exception;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BadRequestExceptionTest {


    private ZonedDateTime getTestTime() {
        return ZonedDateTime.now();
    }

    private List<String> getTestDetails() {
        return List.of("Detail 1", "Detail 2");
    }

    @Test
    void constructorFull_shouldInitializeAllFields() {

        String code = "E400";
        String message = "Solicitud inválida.";
        List<String> details = getTestDetails();
        ZonedDateTime timeStamp = getTestTime();

        BadRequestException exception = new BadRequestException(code, message, details, timeStamp);

        assertEquals(code, exception.getCode(), "El código debe coincidir.");
        assertEquals(message, exception.getMessage(), "El mensaje debe coincidir.");
        assertEquals(details, exception.getDetails(), "Los detalles deben coincidir.");
        assertEquals(timeStamp, exception.getTimeStamp(), "El tiempo debe coincidir.");
    }

    @Test
    void constructorBasic_shouldInitializeCodeAndMessage() {

        String code = "E401";
        String message = "Acceso denegado.";

        BadRequestException exception = new BadRequestException(code, message);

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructorWithDetails_shouldInitializeCodeMessageAndDetails() {

        String code = "E402";
        String message = "Datos incompletos.";
        List<String> details = getTestDetails();

        BadRequestException exception = new BadRequestException(code, message, details);

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertEquals(details, exception.getDetails());
    }


    @Test
    void constructorEmpty_shouldInitializeWithoutArguments() {

        BadRequestException exception = new BadRequestException();

        assertNotNull(exception);
    }

}