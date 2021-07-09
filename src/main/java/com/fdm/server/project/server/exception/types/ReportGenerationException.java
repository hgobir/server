package com.fdm.server.project.server.exception.types;

import com.fdm.server.project.server.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ReportGenerationException extends CustomException {
    public ReportGenerationException(String message) {
        super(message);
    }

    public ReportGenerationException(String message, Long applicationUserId) {
        super(message, applicationUserId);
    }
}
