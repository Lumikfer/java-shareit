package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class ValidException extends  RuntimeException{

    private final String errors;

    public ValidException(String errors) {
        super("Validation failed");
        this.errors = errors;
    }


}


