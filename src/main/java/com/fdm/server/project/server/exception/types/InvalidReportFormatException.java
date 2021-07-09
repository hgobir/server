package com.fdm.server.project.server.exception.types;


import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.exception.CustomException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class InvalidReportFormatException extends CustomException {


    public InvalidReportFormatException(String message) {
        super(message);
    }

    public InvalidReportFormatException(String message, Long applicationUserId) {
        super(message, applicationUserId);
    }
}
