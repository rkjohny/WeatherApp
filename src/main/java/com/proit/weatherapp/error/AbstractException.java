package com.proit.weatherapp.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AbstractException extends RuntimeException {

    private HttpStatus status;

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(Exception exception) {
        super(exception);
    }

    public AbstractException(String message, Exception exception) {
        super(message, exception);
    }

    public AbstractException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public AbstractException(Exception exception, HttpStatus status) {
        super(exception);
        this.status = status;
    }

    public AbstractException(String message, Exception exception, HttpStatus status) {
        super(message, exception);
        this.status = status;
    }
}
