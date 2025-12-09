package com.backandwhite.core.domain.exception;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BaseExceptionTest {

    private List<String> getTestDetails() {
        return List.of("Campo inválido", "Tamaño incorrecto");
    }

    @Test
    void constructorFull_shouldInitializeAllFields() {
        String code = "BASE001";
        String message = "Error de prueba completo.";
        List<String> details = getTestDetails();
        ZonedDateTime timeStamp = ZonedDateTime.now().minusHours(1);

        BaseException exception = new BaseException(code, message, details, timeStamp);

        assertEquals(code, exception.getCode(), "El código debe ser el pasado.");
        assertEquals(message, exception.getMessage(), "El mensaje debe ser el pasado.");
        assertEquals(details, exception.getDetails(), "Los detalles deben ser los pasados.");
        assertEquals(timeStamp, exception.getTimeStamp(), "La marca de tiempo debe ser la pasada.");
    }

    @Test
    void constructorRuntime_shouldBeAccessibleAndSetMessage() {

        BaseException exception = new BaseException("Test message");

        assertEquals("Test message", exception.getMessage());
    }

    @Test
    void constructorCodeMessage_shouldSetCodeMessageAndAutoSetTimeStamp() {
        String code = "BASE002";
        String message = "Error con tiempo automático.";
        ZonedDateTime startTime = ZonedDateTime.now().minusSeconds(1);

        BaseException exception = new BaseException(code, message);

        assertEquals(code, exception.getCode(), "El código debe ser el pasado.");
        assertEquals(message, exception.getMessage(), "El mensaje debe ser el pasado.");

        assertNull(exception.getDetails(), "Los detalles deben ser null por defecto.");

        ZonedDateTime exceptionTime = exception.getTimeStamp();
        assertNotNull(exceptionTime, "La marca de tiempo no debe ser null.");
        assertThat(exceptionTime).isAfterOrEqualTo(startTime);
    }

    @Test
    void constructorCodeMessageDetails_shouldSetAllFieldsAndAutoSetTimeStamp() {
        String code = "BASE003";
        String message = "Error con detalles y tiempo automático.";
        List<String> details = getTestDetails();
        ZonedDateTime startTime = ZonedDateTime.now().minusSeconds(1);

        BaseException exception = new BaseException(code, message, details);

        assertEquals(code, exception.getCode(), "El código debe ser el pasado.");
        assertEquals(message, exception.getMessage(), "El mensaje debe ser el pasado.");
        assertEquals(details, exception.getDetails(), "Los detalles deben ser los pasados.");

        ZonedDateTime exceptionTime = exception.getTimeStamp();
        assertNotNull(exceptionTime, "La marca de tiempo no debe ser null.");
        assertThat(exceptionTime).isAfterOrEqualTo(startTime);
    }

}