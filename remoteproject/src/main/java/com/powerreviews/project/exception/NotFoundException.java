package com.powerreviews.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//some exception here to throw for validation when returning 404
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity not found")
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
