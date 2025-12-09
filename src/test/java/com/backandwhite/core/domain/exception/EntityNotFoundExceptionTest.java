package com.backandwhite.core.domain.exception;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EntityNotFoundExceptionTest {


    private List<String> getTestViolations() {
        return List.of("Campo 'id' no encontrado", "Recurso inexistente");
    }

    @Test
    void constructorBasic_shouldInitializeWithDefaultDetailsAndTimeStamp() {

        String code = "E404";
        String message = "Entidad no encontrada por ID.";

        ZonedDateTime startTime = ZonedDateTime.now();

        EntityNotFoundException exception = new EntityNotFoundException(code, message);

        assertEquals(code, exception.getCode(), "El código debe coincidir.");
        assertEquals(message, exception.getMessage(), "El mensaje debe coincidir.");

        assertNotNull(exception.getDetails(), "Los detalles no deben ser null.");
        assertTrue(exception.getDetails().isEmpty(), "Los detalles deben ser una lista vacía.");

        ZonedDateTime exceptionTime = exception.getTimeStamp();
        assertNotNull(exceptionTime, "La marca de tiempo no debe ser null.");

        assertThat(exceptionTime).isAfterOrEqualTo(startTime.minusSeconds(1));
    }

    @Test
    void constructorWithViolations_shouldInitializeWithPassedViolationsAndTimeStamp() {

        String code = "E404_V";
        String message = "Entidad no encontrada con violaciones.";
        List<String> violations = getTestViolations();

        ZonedDateTime startTime = ZonedDateTime.now();

        EntityNotFoundException exception = new EntityNotFoundException(code, message, violations);

        assertEquals(code, exception.getCode(), "El código debe coincidir.");
        assertEquals(message, exception.getMessage(), "El mensaje debe coincidir.");

        assertEquals(violations, exception.getDetails(), "La lista de violaciones debe coincidir.");

        ZonedDateTime exceptionTime = exception.getTimeStamp();
        assertNotNull(exceptionTime, "La marca de tiempo no debe ser null.");
        assertThat(exceptionTime).isAfterOrEqualTo(startTime.minusSeconds(1));
    }
}