package com.myproject.models.handlers.advices;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.myproject.models.components.MessageComponent;
import com.myproject.models.handlers.https.*;
import com.myproject.models.structures.dtos.ExceptionRep;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.UnknownFormatConversionException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionAdvice {
    public RestExceptionAdvice() {
        this.init();
    }

    private void init() {
        log.info("RestExceptionAdvice initialized");
    }

    protected ResponseEntity<ExceptionRep> buildResponseEntity(ExceptionRep exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }

    @ExceptionHandler({
            JsonParseException.class,
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            InvalidFormatException.class,
            BadRequestException.class,
            HttpMessageNotReadableException.class,
            MissingPathVariableException.class,
            NoSuchMethodError.class
    })
    public ResponseEntity<ExceptionRep> handleBadRequestException(Exception exception) {
        return buildResponseEntity(new ExceptionRep(HttpStatus.BAD_REQUEST, MessageComponent.MESSAGE.getMessage("runtime.exception.bad_request"), exception.getMessage()));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ExceptionRep> handleValidationException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(
                    String.format("[%s] %s [%s]", fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue()));
        }

        return buildResponseEntity(new ExceptionRep(HttpStatus.BAD_REQUEST, MessageComponent.MESSAGE.getMessage("runtime.exception.BAD_REQUEST"), builder.toString()));
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            MethodNotAllowedException.class
    })
    public ResponseEntity<ExceptionRep> handleMethodNotAllowedException(Exception exception) {
        return buildResponseEntity(new ExceptionRep(HttpStatus.METHOD_NOT_ALLOWED, MessageComponent.MESSAGE.getMessage("runtime.exception.METHOD_NOT_ALLOWED"), exception.getMessage()));
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            ForbiddenException.class
    })
    public ResponseEntity<ExceptionRep> handleForbiddenException(Exception exception) {
        return buildResponseEntity(new ExceptionRep(HttpStatus.FORBIDDEN, MessageComponent.MESSAGE.getMessage("runtime.exception.FORBIDDEN"), exception.getMessage()));
    }

    @ExceptionHandler({
            NotFoundException.class,
            NoSuchElementException.class,
            ServletException.class,
            NoResourceFoundException.class
    })
    public ResponseEntity<ExceptionRep> handleNotFoundException(Exception exception) {
        return buildResponseEntity(new ExceptionRep(HttpStatus.NOT_FOUND, MessageComponent.MESSAGE.getMessage("runtime.exception.NOT_FOUND"), exception.getMessage()));
    }

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            ConstraintViolationException.class,
            SQLIntegrityConstraintViolationException.class,
            IllegalStateException.class,
            CannotAcquireLockException.class,
            NullPointerException.class,
            ConflictException.class,
            JpaSystemException.class
    })
    public ResponseEntity<ExceptionRep> handleConflictException(Exception exception) {
        return buildResponseEntity(new ExceptionRep(HttpStatus.CONFLICT, MessageComponent.MESSAGE.getMessage("runtime.exception.CONFLICT"), exception.getMessage()));
    }

    @ExceptionHandler({
            HttpMessageNotWritableException.class,
            HttpMediaTypeNotAcceptableException.class
    })
    public ResponseEntity<ExceptionRep> handleNotAcceptableException(Exception exception) {
        return buildResponseEntity(new ExceptionRep(HttpStatus.NOT_ACCEPTABLE, MessageComponent.MESSAGE.getMessage("runtime.exception.NOT_ACCEPTABLE"), exception.getMessage()));
    }

    @ExceptionHandler({
            TooManyRequestException.class
    })
    public ResponseEntity<ExceptionRep> httpsTooManyRequestException(Exception exception) {
        return buildResponseEntity(new ExceptionRep(HttpStatus.TOO_MANY_REQUESTS, MessageComponent.MESSAGE.getMessage("runtime.exception.TOO_MANY_REQUESTS"), exception.getMessage()));
    }

    @ExceptionHandler({
            RequestTimeOutException.class
    })
    public ResponseEntity<ExceptionRep> httpsRequestTimeOutException(Exception exception) {
        return buildResponseEntity(new ExceptionRep(HttpStatus.REQUEST_TIMEOUT, MessageComponent.MESSAGE.getMessage("runtime.exception.REQUEST_TIMEOUT"), exception.getMessage()));
    }

    @ExceptionHandler({
            UnauthorizedException.class
    })
    public ResponseEntity<ExceptionRep> httpsUnauthorizedException(Exception exception) {
        return buildResponseEntity(new ExceptionRep(HttpStatus.UNAUTHORIZED, MessageComponent.MESSAGE.getMessage("runtime.exception.UNAUTHORIZED"), exception.getMessage()));
    }

    @ExceptionHandler({
            InternalServerException.class,
            IOException.class,
            Exception.class,
            UnknownFormatConversionException.class
    })
    public ResponseEntity<ExceptionRep> handleInternalServerErrorException(Exception exception, HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        // TODO (Component로 분리 필요) Swagger 관련 요청 제외
        if (requestUri.startsWith("/v3/api-docs") || requestUri.startsWith("/swagger-ui")) {
            return ResponseEntity.ok().build(); // Swagger 요청은 통과
        }
        return buildResponseEntity(new ExceptionRep(HttpStatus.INTERNAL_SERVER_ERROR, MessageComponent.MESSAGE.getMessage("runtime.exception.INTERNAL_SERVER_ERROR"), exception.getMessage()));
    }
}
