package se.anas.taxiCompmay.exceptionHandling;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Error {
    private String message;
    // this to format the date String in Json response
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yy  hh:mm:ss")
    private Date timestamp;

    private String urlMessage;


    public Error(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
    // we create another type of error object with more attributes to use it ex in wronge url exception
    public Error(String message, Date timestamp, String urlMessage) {
        this.message = message;
        this.timestamp = timestamp;
        this.urlMessage = urlMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Error{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
