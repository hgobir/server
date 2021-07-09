package com.fdm.server.project.server.model;

import com.fdm.server.project.server.entity.ApplicationUser;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ExceptionModel {
    private final String message;
    private final HttpStatus httpStatus;
    private final int httpStatusCode;
    private Throwable throwable;
    private final ZonedDateTime zonedDateTime;
    private Long applicationUserId;

    public ExceptionModel(String message, HttpStatus httpStatus, int httpStatusCode, Throwable throwable, ZonedDateTime zonedDateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
        this.throwable = throwable;
        this.zonedDateTime = zonedDateTime;
    }

    public ExceptionModel(String message, HttpStatus httpStatus, int httpStatusCode, ZonedDateTime zonedDateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
        this.zonedDateTime = zonedDateTime;
    }

    public ExceptionModel(String message, HttpStatus httpStatus, int httpStatusCode, Throwable throwable, ZonedDateTime zonedDateTime, Long applicationUserId) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
        this.throwable = throwable;
        this.zonedDateTime = zonedDateTime;
        this.applicationUserId = applicationUserId;
    }

    public String getMessage() {
        return message;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }



    public Long getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(Long applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
