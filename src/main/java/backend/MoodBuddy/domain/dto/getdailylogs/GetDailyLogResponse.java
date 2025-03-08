package backend.MoodBuddy.domain.dto.getdailylogs;

import backend.MoodBuddy.domain.dto.ResponseHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the class for daily log response
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDailyLogResponse {
    private GetDailyLogResponseBody responseBody;
    private ResponseHeader responseHeader;
}