package com.backandwhite.core.domain.exception;

import lombok.Getter;

@Getter
public enum Messages {

    ENTITY_NOT_FOUND("NF001", "No se ha encontrado el registro con id %s");

    private String code;
    private String messsage;

    Messages(String code, String messsage) {
        this.code = code;
        this.messsage = messsage;
    }
}