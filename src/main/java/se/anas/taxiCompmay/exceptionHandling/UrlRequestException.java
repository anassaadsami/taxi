package se.anas.taxiCompmay.exceptionHandling;

import org.springframework.http.HttpStatus;

public class UrlRequestException extends TaxiCompanyException {

    public UrlRequestException() {
        super();

    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.METHOD_NOT_ALLOWED;
    }
}

