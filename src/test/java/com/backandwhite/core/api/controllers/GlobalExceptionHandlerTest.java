package com.backandwhite.core.api.controllers;

import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.domain.exception.BadRequestException;
import com.backandwhite.core.domain.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handleValidation_shouldReturnBadRequest_withValidationErrors() {

        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("userDto", "name", "no debe estar vacío");
        FieldError fieldError2 = new FieldError("userDto", "email", "no debe estar vacío");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidation(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorResponse error = response.getBody();
        assertNotNull(error);
        assertEquals(GlobalExceptionHandler.BR_001, error.getCode());
        assertThat(error.getMessage()).contains("name: no debe estar vacío, email: no debe estar vacío");
        assertThat(error.getDetails().isEmpty());
        assertNotNull(error.getTimeStamp());
    }

    @Test
    void handleEntityNotFoundException_shouldReturnNotFound() {
        String code = "NF404";
        String message = "Recurso no encontrado";
        List<String> details = List.of("ID: 5");

        EntityNotFoundException ex = new EntityNotFoundException(code, message, details);
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleEntityNotFoundException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ErrorResponse error = response.getBody();
        assertNotNull(error);
        assertEquals(code, error.getCode());
        assertEquals(message, error.getMessage());
        assertEquals(details, error.getDetails());
    }

    @Test
    void handleBadRequestException_shouldReturnBadRequest() {

        String code = "BR400";
        String message = "Falta un campo requerido";
        List<String> details = List.of("Campo 'X' obligatorio");

        BadRequestException ex = new BadRequestException( code, message, details);
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleBadRequestException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorResponse error = response.getBody();
        assertNotNull(error);
        assertEquals(code, error.getCode());
        assertEquals(message, error.getMessage());
        assertEquals(details, error.getDetails());
    }

    @Test
    void testHandleHttpMessageNotReadable_noSpecificCause() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("error", null);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleHttpMessageNotReadableException(ex);

        assertEquals("BR002", response.getBody().getCode());
        assertEquals("Solicitud Inválida: Formato de cuerpo incorrecto.", response.getBody().getMessage());
        assertEquals(List.of("El cuerpo de la solicitud (Request body) no es legible o está mal formado. Asegúrate de que el formato JSON sea válido y los tipos de datos sean correctos."), response.getBody().getDetails());
        assertNotNull(response.getBody().getTimeStamp());
    }

    @Test
    void testHandleDataIntegrityViolation_duplicateKey() {
        Throwable root = new RuntimeException("duplicate key value violates unique constraint");
        DataIntegrityViolationException ex = new DataIntegrityViolationException("error", root);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDataIntegrityViolationException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("BR003", response.getBody().getCode());
        assertEquals("El recurso ya existe.", response.getBody().getMessage());
        assertEquals(List.of(root.getMessage()), response.getBody().getDetails());
        assertNotNull(response.getBody().getTimeStamp());
    }
}