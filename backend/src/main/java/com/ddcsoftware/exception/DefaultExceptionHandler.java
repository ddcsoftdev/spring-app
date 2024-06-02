package com.ddcsoftware.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler for handling specific exceptions across the whole application.
 * <p>
 * This class uses {@link ControllerAdvice} to handle exceptions globally in a single location.
 * </p>
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * Handles {@link ResourceNotFoundException} by returning a custom error response.
     *
     * @param e        the exception to handle
     * @param request  the HTTP request that resulted in the exception
     * @param response the HTTP response
     * @return a {@link ResponseEntity} containing the custom error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleException (ResourceNotFoundException e,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * This one used for auth 403 exception
     * @param e
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiError> handleException (InsufficientAuthenticationException e,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleException (BadCredentialsException e,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    /**
     * This one used a general exception
     * @param e
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException (Exception e,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
