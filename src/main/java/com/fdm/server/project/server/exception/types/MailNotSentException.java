package com.fdm.server.project.server.exception.types;

import com.fdm.server.project.server.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
public class MailNotSentException extends CustomException {
    public MailNotSentException(String message) {
        super(message);
    }

    public MailNotSentException(String message, Long applicationUserId) {
        super(message, applicationUserId);
    }
}
