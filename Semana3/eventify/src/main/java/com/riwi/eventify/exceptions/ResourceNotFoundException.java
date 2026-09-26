package com.riwi.eventify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Al lanzar esta excepción, Spring automáticamente intercepta y responde un HTTP 404.
// Esto evita saturar los controladores con bloques try-catch.
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}