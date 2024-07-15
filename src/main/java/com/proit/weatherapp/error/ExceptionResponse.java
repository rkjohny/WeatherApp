package com.proit.weatherapp.error;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@AllArgsConstructor
public class ExceptionResponse implements ErrorResponse {
    private HttpStatus status;
    private ExceptionDetails details;


    @Override
    public HttpStatusCode getStatusCode() {
        return status;
    }

    @Override
    public ProblemDetail getBody() {
        return details;
    }
}
