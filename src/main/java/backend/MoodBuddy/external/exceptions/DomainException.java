package backend.MoodBuddy.external.exceptions;

import lombok.Getter;

@Getter
public class DomainException extends BaseException {
    private String requestId;

    public DomainException(String message) {
        super(message, message);
    }

    public DomainException(String message, String code) {
        super(message, code);
    }

    public DomainException(Exception exception, String message) {
        super(exception, message, message);
    }

    public DomainException(Exception exception, String code, String message) {
        super(exception, code, message);
    }

    public DomainException(String message, String code, String requestId) {
        super(message, code);
        this.requestId = requestId;
    }

}
