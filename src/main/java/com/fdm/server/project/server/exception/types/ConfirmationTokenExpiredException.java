package com.fdm.server.project.server.exception.types;

import com.fdm.server.project.server.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
public class ConfirmationTokenExpiredException extends CustomException {


    public ConfirmationTokenExpiredException(String message) {
        super(message);
    }

    public ConfirmationTokenExpiredException(String message, Long applicationUserId) {
        super(message, applicationUserId);
    }
}
