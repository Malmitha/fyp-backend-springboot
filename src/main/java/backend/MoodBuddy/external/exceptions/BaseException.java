package backend.MoodBuddy.external.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BaseException extends Exception{
    private String code;

    public BaseException(Exception exception, String message, String s) {

        super(message);
    }

    public BaseException(String message, String code) {

        super(message);
        this.code = code;
    }
}
