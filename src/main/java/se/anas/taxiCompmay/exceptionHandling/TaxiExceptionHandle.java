package se.anas.taxiCompmay.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice    // make this class responsible to handle globally exceptions
public class TaxiExceptionHandle {

    // make this method responsible to handle with TaxiCompanyException , and
    // we can add here many Exception also
    /*
    @ExceptionHandler(TaxiCompanyException.class)
    public ResponseEntity<Object> handleTaxiExceptions(TaxiCompanyException ex, WebRequest request) {
        Error error = new Error(ex.getMessage(), new Date());
        return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
    }

     */

    @ExceptionHandler(TaxiCompanyException.class)
    public ResponseEntity<Object> handleTaxiExceptions(TaxiCompanyException ex) {
        Error error = new Error(ex.getMessage(), new Date());
        return new ResponseEntity<Object>(error, ex.getHttpStatus());
    }


    /*
    // if we write wronge url so who we change our response in josn
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e
            , HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new Error(e.getMessage(), new Date())
                , HttpStatus.NOT_FOUND);
    }

     */


}

