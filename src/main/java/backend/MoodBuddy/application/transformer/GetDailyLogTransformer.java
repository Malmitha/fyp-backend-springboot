package backend.MoodBuddy.application.transformer;

import backend.MoodBuddy.domain.dto.getdailylogs.GetDailyLogResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the Daily log Transformer class that is used to map the response
 */

@Component
public class GetDailyLogTransformer implements ResponseEntityInterface{
    @Override
    public Map transform(Object entity) {
        GetDailyLogResponse response = (GetDailyLogResponse) entity;
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("responseBody", response.getResponseBody());
        mapping.put("responseHeader", response.getResponseHeader());

        return mapping;
    }
}
