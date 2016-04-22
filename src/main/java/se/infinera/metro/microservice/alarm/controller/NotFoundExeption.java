package se.infinera.metro.microservice.alarm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExeption extends Exception {
    public NotFoundExeption(String message) {
        super(message);
    }
}
