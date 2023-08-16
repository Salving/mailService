package salving.mailservice.mailing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MailingNotFoundException extends RuntimeException {
    public MailingNotFoundException() {
        super();
    }

    public MailingNotFoundException(String message) {
        super(message);
    }

    public MailingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailingNotFoundException(Throwable cause) {
        super(cause);
    }
}
