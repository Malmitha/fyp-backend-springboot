package backend.MoodBuddy.application.transformer;

import backend.MoodBuddy.domain.dto.getprofile.GetProfileResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the Get profile Transformer class that is used to map the response
 */
@Component
public class GetProfileTransformer implements ResponseEntityInterface{
    @Override
    public Map transform(Object entity) {
        GetProfileResponse response = (GetProfileResponse) entity;
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("responseBody", response.getResponseBody());
        mapping.put("responseHeader", response.getResponseHeader());

        return mapping;
    }
}
