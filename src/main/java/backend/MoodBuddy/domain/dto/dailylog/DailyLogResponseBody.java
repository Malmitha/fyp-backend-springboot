package backend.MoodBuddy.domain.dto.dailylog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the class for daily log response body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyLogResponseBody {
    private Long DailyDataLogId;
    private MoodLogs moodLog;
    private String message;
}
