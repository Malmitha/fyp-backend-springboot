package backend.MoodBuddy.application.transformer;

import java.util.Map;

public interface ResponseEntityInterface {
    default Map transform(Object entity) {
        return null;
    }
}