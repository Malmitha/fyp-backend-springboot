package backend.MoodBuddy.domain.dto.loghistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This is the class for log response body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogResponseBody {
    private List<Logs> logHistory;
}
