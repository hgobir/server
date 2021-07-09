package com.fdm.server.project.server.exception.types;

import com.fdm.server.project.server.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAuthenticatedException extends CustomException {


    public NotAuthenticatedException(String message) {
        super(message);
    }

    public NotAuthenticatedException(String message, Long applicationUserId) {
        super(message, applicationUserId);
    }
}
