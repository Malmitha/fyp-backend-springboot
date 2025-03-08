package backend.MoodBuddy.domain.dto.getdailylogs;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This dto class to get the mood log info
 */
@Data
public class AllMoodLogs {
    private Long logId;
    private int rating;
    private String category;
    private String type;
    private LocalDate logDate;
    private LocalTime logTime;

    public AllMoodLogs(Long logId, int rating, String category, String type, LocalDate logDate, LocalTime logTime){
        this.logId = logId;
        this.rating = rating;
        this.category = category;
        this.type = type;
        this.logDate = logDate;
        this.logTime = logTime;
    }
}
