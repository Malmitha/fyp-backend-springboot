package backend.MoodBuddy.application.transformer;

import backend.MoodBuddy.domain.dto.login.LoginResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the Login Transformer class that is used to map the response
 */
@Component
public class LoginTransformer implements ResponseEntityInterface{
    @Override
    public Map transform(Object entity) {
        LoginResponse response = (LoginResponse) entity;
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("responseBody", response.getResponseBody());
        mapping.put("responseHeader", response.getResponseHeader());

        return mapping;
    }
}
