package com.backandwhite.core.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MessagesTest {

    @Test
    void entityNotFound_shouldHaveCorrectCodeAndMessage() {

        Messages entityNotFound = Messages.ENTITY_NOT_FOUND;

        assertNotNull(entityNotFound);

        assertEquals("NF001", entityNotFound.getCode(), "El código de ENTITY_NOT_FOUND debe ser 'NF001'.");

        assertEquals("No se ha encontrado el registro con id %s", entityNotFound.getMessage(),
                "El mensaje debe coincidir con el texto de formato.");

        assertThat(Messages.values()).hasSize(1);
    }
}