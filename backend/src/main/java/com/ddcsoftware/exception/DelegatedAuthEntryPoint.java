package com.ddcsoftware.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Component to handle authentication exceptions by delegating to the {@link HandlerExceptionResolver}.
 * This class implements {@link AuthenticationEntryPoint} to provide a custom entry point for authentication failures.
 * It resolves the exception using the injected {@link HandlerExceptionResolver}.
 */
@Component
public class DelegatedAuthEntryPoint implements AuthenticationEntryPoint {

    //injection
    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Constructs a new {@code DelegatedAuthEntryPoint} with the specified {@link HandlerExceptionResolver}.
     *
     * @param handlerExceptionResolver the handler exception resolver to delegate authentication exceptions to
     */
    public DelegatedAuthEntryPoint(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * Commences an authentication scheme.
     * <p>
     * This method is called when an authentication exception is thrown. It delegates the exception handling
     * to the {@link HandlerExceptionResolver}.
     * </p>
     *
     * @param request       that resulted in an {@link AuthenticationException}
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException      in case of an input/output error
     * @throws ServletException in case of a servlet error
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}
