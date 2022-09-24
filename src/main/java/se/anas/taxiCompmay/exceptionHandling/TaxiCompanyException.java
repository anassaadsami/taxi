package se.anas.taxiCompmay.exceptionHandling;

import org.springframework.http.HttpStatus;

public abstract class TaxiCompanyException extends RuntimeException {

    private String message;

    public TaxiCompanyException(Object resource, Long id) {   // constructor
        this.message = getMessage(resource, id);
    }

    public TaxiCompanyException() {   // another constructor which takes one parameter
        this.message = "url request is not found";
    }

    // abstract method and every exception class must implement it in its own way
    public abstract HttpStatus getHttpStatus();

    @Override  // getters
    public String getMessage() {
        return message;
    }

    /*
    this return different String messages depends on which class we pass to the orElseThrow method in service class
    here we want to get just the class name( which found after last . in toString )
     of the object which accessed as a parameter
     and by this way it will be general to many different classes and return the class name
     */
    public String getMessage(Object resource, Long id) {
        String resourceName = resource.toString();
        int i = resourceName.lastIndexOf('.') + 1;
        String className = resourceName.substring(i);
        if (className.equals("User"))
            this.message = String.format("%s Not Found with id %d", className, id);
        else if (className.equals("Taxi"))
            this.message = String.format("%s no %d Not Found in or company", className, id);
        return this.message;
    }


}

