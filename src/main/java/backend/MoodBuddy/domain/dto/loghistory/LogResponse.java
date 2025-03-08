package backend.MoodBuddy.domain.dto.loghistory;

import backend.MoodBuddy.domain.dto.ResponseHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the class for log response
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogResponse {
    private LogResponseBody responseBody;
    private ResponseHeader responseHeader;
}