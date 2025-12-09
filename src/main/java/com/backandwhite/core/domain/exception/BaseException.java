package com.backandwhite.core.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    private String code;
    private String message;
    private List<String> details;
    private ZonedDateTime timeStamp;

    public BaseException(String message) {
        this.message = message;
    }

    public BaseException(String code, String message) {
        this.code = code;
        this.message = message;
        this.timeStamp = ZonedDateTime.now();
    }

    public BaseException(String code, String message, List<String> details) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.timeStamp = ZonedDateTime.now();
    }
}
