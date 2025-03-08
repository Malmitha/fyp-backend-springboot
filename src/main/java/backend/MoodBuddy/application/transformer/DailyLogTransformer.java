package backend.MoodBuddy.application.transformer;

import backend.MoodBuddy.domain.dto.dailylog.DailyLogResponse;
import backend.MoodBuddy.domain.dto.getprofile.GetProfileResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the Daily log Transformer class that is used to map the response
 */

@Component
public class DailyLogTransformer implements ResponseEntityInterface{
    @Override
    public Map transform(Object entity) {
        DailyLogResponse response = (DailyLogResponse) entity;
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("responseBody", response.getResponseBody());
        mapping.put("responseHeader", response.getResponseHeader());

        return mapping;
    }
}
