package com.fdm.server.project.server.exception.types;


import com.fdm.server.project.server.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UsernameDoesNotExistException extends CustomException {
    public UsernameDoesNotExistException(String message) {
        super(message);
    }

    public UsernameDoesNotExistException(String message, Long applicationUserId) {
        super(message, applicationUserId);
    }
}
