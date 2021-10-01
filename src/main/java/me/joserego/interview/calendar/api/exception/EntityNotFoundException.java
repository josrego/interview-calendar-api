package me.joserego.interview.calendar.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException  extends RuntimeException {
    private static final long serialVersionUID = -2261979038242275243L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
