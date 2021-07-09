package com.fdm.server.project.server.exception.types;

import com.fdm.server.project.server.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConfirmationTokenNotFoundException extends CustomException {


    public ConfirmationTokenNotFoundException(String message) {
        super(message);
    }

    public ConfirmationTokenNotFoundException(String message, Long applicationUserId) {
        super(message, applicationUserId);
    }
}
