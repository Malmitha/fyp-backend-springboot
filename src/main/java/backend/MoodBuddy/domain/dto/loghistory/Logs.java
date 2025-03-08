package backend.MoodBuddy.domain.dto.loghistory;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This dto class to get the activity info
 */
@Data
public class Logs {
    private Long logId;
    private LocalDate loggedInDate;
    private LocalTime loggedInTime;
    private LocalDate loggedOutDate;
    private LocalTime loggedOutTime;
    private double sessionDurationHours;

    public Logs(Long id, LocalDate loggedInDate, LocalTime loggedInTime, LocalDate loggedOutDate,
                LocalTime loggedOutTime, double sessionDurationHours){
        this.logId = id;
        this.loggedInDate = loggedInDate;
        this.loggedInTime = loggedInTime;
        this.loggedOutDate = loggedOutDate;
        this.loggedOutTime = loggedOutTime;
        this.sessionDurationHours = sessionDurationHours;
    }
}
