package backend.MoodBuddy.application.transformer;

import backend.MoodBuddy.domain.dto.loghistory.LogResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the Daily log Transformer class that is used to map the response
 */

@Component
public class LogHistoryTransformer implements ResponseEntityInterface{
    @Override
    public Map transform(Object entity) {
        LogResponse response = (LogResponse) entity;
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("responseBody", response.getResponseBody());
        mapping.put("responseHeader", response.getResponseHeader());

        return mapping;
    }
}
