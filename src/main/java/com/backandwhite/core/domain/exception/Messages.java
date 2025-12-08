package com.backandwhite.core.domain.exception;

import lombok.Getter;

@Getter
public enum Messages {

    ENTITY_NOT_FOUND("NF001", "No se ha encontrado el registro con id %s");

    private final String code;
    private final String message;

    Messages(String code, String message) {
        this.code = code;
        this.message = message;
    }
}