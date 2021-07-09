package com.fdm.server.project.server.exception.types;

import com.fdm.server.project.server.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiRequestException extends CustomException {


    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Long applicationUserId) {
        super(message, applicationUserId);
    }
}
