package com.fdm.server.project.server.exception;

public class CustomException extends RuntimeException {

    private Long applicationUserId;


    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Long applicationUserId) {
        super(message);
        this.applicationUserId = applicationUserId;

    }

    public Long getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(Long applicationUserId) {
        this.applicationUserId = applicationUserId;
    }
}
