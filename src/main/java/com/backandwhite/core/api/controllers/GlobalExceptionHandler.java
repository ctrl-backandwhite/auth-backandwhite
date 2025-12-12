package com.backandwhite.core.api.controllers;

import com.backandwhite.core.api.dtos.out.ErrorResponse;
import com.backandwhite.core.domain.exception.BadRequestException;
import com.backandwhite.core.domain.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String BR_001 = "BR001";
    public static final String BR_002 = "BR002";
    public static final String BR_003 = "BR003";
    public static final String FORMATO_DE_CUERPO_INCORRECTO = "Solicitud Inválida: Formato de cuerpo incorrecto.";
    public static final String BR_DETAIL = "El cuerpo de la solicitud (Request body) no es legible o está mal formado. " +
            "Asegúrate de que el formato JSON sea válido y los tipos de datos sean correctos.";
    public static final String ERROR_DE_SINTAXIS_JSON = "Error de sintaxis JSON: ";
    public static final String ERROR_DE_INTEGRIDAD_DE_DATOS = "Error de Integridad de Datos.";
    public static final String RECURSO_YA_EXISTE = "El recurso ya existe.";
    public static final String RESTRICCION_DE_LA_BASE_DE_DATOS = "Error de integridad de datos. La operación viola una restricción de la base de datos.";
    public static final String VIOLACION_DE_UNA_RESTRICCION_DE_UNICIDAD = "Ya existe un recurso con este valor.";
    public static final String DUPLICATE_KEY = "duplicate key";
    public static final String PUEDEN_SER_NULOS_Y_NO_FUERON_PROPORCIONADOS = "Uno o más campos obligatorios no pueden ser nulos y no fueron proporcionados.";
    public static final String CANNOT_BE_NULL = "cannot be null";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": no debe estar vacío")
                .collect(Collectors.joining(", "));

        ErrorResponse error = ErrorResponse.builder()
                .code(BR_001)
                .message(message)
                .details(new ArrayList<>())
                .timeStamp(ZonedDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public  ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        String detail = BR_DETAIL;
        if (ex.getCause() instanceof com.fasterxml.jackson.databind.JsonMappingException) {
            detail = ex.getCause().getMessage();
        } else if (ex.getCause() instanceof com.fasterxml.jackson.core.JsonParseException) {
            detail = ERROR_DE_SINTAXIS_JSON + ex.getCause().getMessage();
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(BR_002)
                .message(FORMATO_DE_CUERPO_INCORRECTO)
                .details(List.of(detail))
                .timeStamp(ZonedDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        String detailMessage = RESTRICCION_DE_LA_BASE_DE_DATOS;

        String rootCause = (ex.getRootCause() != null) ? ex.getRootCause().getMessage() : ex.getMessage();
        String safeRootCause = (rootCause == null || rootCause.isBlank())
                ? "No se recibió detalle adicional del error."
                : rootCause;

        if (safeRootCause.toLowerCase().contains(DUPLICATE_KEY)) {

            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(BR_003)
                    .message(RECURSO_YA_EXISTE)
                    .details(List.of(safeRootCause))
                    .timeStamp(ZonedDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        if (safeRootCause.toLowerCase().contains(CANNOT_BE_NULL)) {
            detailMessage = PUEDEN_SER_NULOS_Y_NO_FUERON_PROPORCIONADOS;
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(BR_001)
                .message(ERROR_DE_INTEGRIDAD_DE_DATOS)
                .details(List.of(detailMessage, safeRootCause))
                .timeStamp(ZonedDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public  ResponseEntity<ErrorResponse> handlePropertyReferenceException(PropertyReferenceException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(BR_002)
                .message("La propiedad por la que intenta filtrar no existe en la entidad.")
                .details(List.of(
                        "Revise las propiedades que tiene su entidad.",
                        "Recuerde que debe informarla en este formato 'propertieName:desc' o 'propertieName:asc'.",
                        "Ejemplo: 'id:asc' o 'id:desc'."
                ))
                .timeStamp(ZonedDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .details(ex.getDetails().isEmpty() ? List.of(): ex.getDetails())
                .timeStamp(ZonedDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .details(ex.getDetails().isEmpty() ? List.of(): ex.getDetails())
                .timeStamp(ZonedDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
