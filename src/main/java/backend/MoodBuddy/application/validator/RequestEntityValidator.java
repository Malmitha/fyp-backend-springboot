package backend.MoodBuddy.application.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
public class RequestEntityValidator {
    @Autowired
    private Validator validator;

    public RequestEntityValidator() {
    }

    public void validate(RequestEntityInterface target) throws ValidationException {
        Set<ConstraintViolation<RequestEntityInterface>> errors = this.validator.validate(target, new Class[0]);
        if (!errors.isEmpty()) {
            throw new ValidationException(String.valueOf(errors));
        }
    }
}
