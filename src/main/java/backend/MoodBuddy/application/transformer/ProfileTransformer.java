package backend.MoodBuddy.application.transformer;

import backend.MoodBuddy.domain.dto.profile.ProfileResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the Create profile Transformer class that is used to map the response
 */
@Component
public class ProfileTransformer implements ResponseEntityInterface{
    @Override
    public Map transform(Object entity) {
        ProfileResponse response = (ProfileResponse) entity;
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("responseBody", response.getResponseBody());
        mapping.put("responseHeader", response.getResponseHeader());

        return mapping;
    }
}
