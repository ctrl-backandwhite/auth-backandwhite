package com.backandwhite.core.domain.exception;

import java.time.ZonedDateTime;
import java.util.List;

public class BadRequestException extends BaseException {

    public BadRequestException(String code, String message, List<String> details, ZonedDateTime timeStamp) {
        super(code, message, details, timeStamp);
    }

    public BadRequestException() {
    }

    public BadRequestException(String code, String message) {
        super(code, message);
    }

    public BadRequestException(String code, String message, List<String> details) {
        super(code, message, details);
    }
}
