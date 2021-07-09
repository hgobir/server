package com.fdm.server.project.server.exception;

import com.fdm.server.project.server.exception.types.*;
import com.fdm.server.project.server.model.ExceptionModel;
import com.fdm.server.project.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final AdminService adminService;

    @Autowired
    public ExceptionController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ExceptionHandler(value = {UsernameNotFoundInServerException.class})
    public ResponseEntity<Object> handleUsernameNotFoundInServerException(UsernameNotFoundInServerException e) {


        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        ExceptionModel usernameNotFoundInServerException = new ExceptionModel(
                e.getMessage(),
                notFound,
                notFound.value(),
                ZonedDateTime.now(ZoneId.of("Z")));


        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                notFound,
                notFound.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(notFound)
                .contentType(MediaType.APPLICATION_JSON)
                .body(usernameNotFoundInServerException);

        return responseEntity;
    }

    @ExceptionHandler(value = {MailNotSentException.class})
    public ResponseEntity<Object> handleMailNotSendException(MailNotSentException e) {


        final HttpStatus failedDependency = HttpStatus.FAILED_DEPENDENCY;
        ExceptionModel mailException = new ExceptionModel(
                e.getMessage(),
                failedDependency,
                failedDependency.value(),
                ZonedDateTime.now(ZoneId.of("Z")));

        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                failedDependency,
                failedDependency.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(failedDependency)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mailException);

        return responseEntity;
    }


    @ExceptionHandler(value = {InvalidReportFormatException.class})
    public ResponseEntity<Object> handleInvalidReportFormatException(InvalidReportFormatException e) {


        final HttpStatus unsupportedMediaType = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

        ExceptionModel invalidReportException = new ExceptionModel(
                                                        e.getMessage(),
                                                        unsupportedMediaType,
                                                        unsupportedMediaType.value(),
                                                        ZonedDateTime.now(ZoneId.of("Z")));

        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                unsupportedMediaType,
                unsupportedMediaType.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(unsupportedMediaType)
                .contentType(MediaType.APPLICATION_JSON)
                .body(invalidReportException);

        return responseEntity;
    }
    

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {


        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionModel apiException = new ExceptionModel(
                e.getMessage(),
                badRequest,
                badRequest.value(),
                ZonedDateTime.now(ZoneId.of("Z")));

        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                badRequest,
                badRequest.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(badRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .body(apiException);

        return responseEntity;
    }

    @ExceptionHandler(value = {NotAuthenticatedException.class})
    public ResponseEntity<Object> handleNotAuthenticatedException(NotAuthenticatedException e) {


        final HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                unauthorized,
                unauthorized.value(),
                ZonedDateTime.now(ZoneId.of("Z")));

        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                unauthorized,
                unauthorized.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(unauthorized)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionModel);

        return responseEntity;
    }

    @ExceptionHandler(value = {ConfirmationTokenNotFoundException.class})
    public ResponseEntity<Object> handleConfirmationTokenNotFoundException(ConfirmationTokenNotFoundException e) {

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                notFound,
                notFound.value(),
                ZonedDateTime.now(ZoneId.of("Z")));

        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                notFound,
                notFound.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(notFound)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionModel);

        return responseEntity;
    }

    @ExceptionHandler(value = {ConfirmationTokenExpiredException.class})
    public ResponseEntity<Object> handleConfirmationTokenExpiredException(ConfirmationTokenExpiredException e) {

        final HttpStatus requestTimeout = HttpStatus.REQUEST_TIMEOUT;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                requestTimeout,
                requestTimeout.value(),
                ZonedDateTime.now(ZoneId.of("Z")));

        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                requestTimeout,
                requestTimeout.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(requestTimeout)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionModel);

        return responseEntity;
    }

    @ExceptionHandler(value = {EmailAlreadyVerifiedException.class})
    public ResponseEntity<Object> handleEmailAlreadyVerifiedException(EmailAlreadyVerifiedException e) {

        final HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                notAcceptable,
                notAcceptable.value(),
                ZonedDateTime.now(ZoneId.of("Z")));

        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                notAcceptable,
                notAcceptable.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(notAcceptable)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionModel);

        return responseEntity;
    }

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    public ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {

        final HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                notAcceptable,
                notAcceptable.value(),
                ZonedDateTime.now(ZoneId.of("Z")));


        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                notAcceptable,
                notAcceptable.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(notAcceptable)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionModel);

        return responseEntity;
    }

    @ExceptionHandler(value = {EmailDoesNotExistException.class})
    public ResponseEntity<Object> handleEmailDoesNotExistException(EmailDoesNotExistException e) {

        final HttpStatus notFound = HttpStatus.NOT_FOUND;

        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                notFound,
                notFound.value(),
                ZonedDateTime.now(ZoneId.of("Z")));

        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                notFound,
                notFound.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(notFound)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionModel);

        return responseEntity;
    }

    @ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {

        final HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                notAcceptable,
                notAcceptable.value(),
                ZonedDateTime.now(ZoneId.of("Z")));

        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                notAcceptable,
                notAcceptable.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(notAcceptable)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionModel);

        return responseEntity;
    }

    @ExceptionHandler(value = {EmailNotValidException.class})
    public ResponseEntity<Object> handleEmailNotValidException(EmailNotValidException e) {

        final HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                notAcceptable,
                notAcceptable.value(),
                ZonedDateTime.now(ZoneId.of("Z")));


        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                notAcceptable,
                notAcceptable.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(notAcceptable)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionModel);

        return responseEntity;
    }


    @ExceptionHandler(value = {TradeException.class})
    public ResponseEntity<Object> handleTradeException(TradeException e) {

        final HttpStatus preconditionFailed = HttpStatus.PRECONDITION_FAILED;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                preconditionFailed,
                preconditionFailed.value(),
                ZonedDateTime.now(ZoneId.of("Z")));


        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                preconditionFailed,
                preconditionFailed.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                e.getApplicationUserId()));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(preconditionFailed)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionModel);

        return responseEntity;
    }

    public ResponseEntity<Object> handleReportGenerationException(ReportGenerationException e) {

        final HttpStatus expectationFailed = HttpStatus.EXPECTATION_FAILED;
        ExceptionModel exceptionModel = new ExceptionModel(
                e.getMessage(),
                expectationFailed,
                expectationFailed.value(),
                ZonedDateTime.now(ZoneId.of("Z")));


        adminService.sendExceptionEmail(new ExceptionModel(
                e.getMessage(),
                expectationFailed,
                expectationFailed.value(),
                e,
                ZonedDateTime.now(ZoneId.of("Z")),
                0L));

        ResponseEntity<Object> responseEntity = ResponseEntity.status(expectationFailed)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionModel);

        return responseEntity;
    }







}
