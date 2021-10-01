package me.joserego.interview.calendar.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AbstractController {
    ResponseEntity successful() {
        return ResponseEntity.ok()
                .build();
    }

    ResponseEntity successful(Object object) {
        return ResponseEntity.ok()
                .body(object);
    }

    <T> ResponseEntity created(T object) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(object);
    }

    ResponseEntity conflict() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .build();
    }

    ResponseEntity notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    ResponseEntity badRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
    }

}
