package backend.MoodBuddy.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the class for the request header
 */
@Data
@NoArgsConstructor

public class RequestHeader {
    @NotNull(message = "requestId can't be null or empty")
    private String requestId;

    @NotNull(message = "timestamp can't be null or empty")
    private String timestamp;

    public RequestHeader(@NotNull(message = "requestId can't be null or empty") String requestId,
                         @NotNull(message = "timestamp can't be null or empty") String timestamp) {
        this.requestId = requestId;
        this.timestamp = timestamp;
    }
}
