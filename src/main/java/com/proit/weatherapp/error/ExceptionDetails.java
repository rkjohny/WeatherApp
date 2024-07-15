package com.proit.weatherapp.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

@Getter
@Setter
public class ExceptionDetails extends ProblemDetail {
    String details;
    int status;

    public ExceptionDetails(String details) {
        super.setDetail(details);
        this.details = details;
    }

    public ExceptionDetails(int status, String details) {
        super.setStatus(status);
        super.setDetail(details);
        this.details = details;
        this.status = status;
    }
}
