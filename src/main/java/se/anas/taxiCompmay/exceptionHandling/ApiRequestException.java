package se.anas.taxiCompmay.exceptionHandling;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends TaxiCompanyException{

    public ApiRequestException(Object resource, Long id) {
        super(resource, id);

    }

     @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}


