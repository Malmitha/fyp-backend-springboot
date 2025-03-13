package backend.MoodBuddy.domain.dto.getdailylogs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * This is the class for daily log response body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDailyLogResponseBody {
    private List<LocalDate> dates;
    private List<DailyDataLogs> dailyLogs;
    private List<AllMoodLogs> moodLogs;
}
