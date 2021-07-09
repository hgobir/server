package com.fdm.server.project.server.exception.types;

import com.fdm.server.project.server.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class TradeException extends CustomException {


    public TradeException(String message) {
        super(message);
    }

    public TradeException(String message, Long applicationUserId) {
        super(message, applicationUserId);
    }
}
