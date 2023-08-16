package salving.mailservice.postoffice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PostOfficeNotFoundException extends RuntimeException {

    public PostOfficeNotFoundException() {
        super();
    }

    public PostOfficeNotFoundException(String message) {
        super(message);
    }

    public PostOfficeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostOfficeNotFoundException(Throwable cause) {
        super(cause);
    }
}
