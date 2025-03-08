package backend.MoodBuddy.domain.dto.getdailylogs;

import lombok.Data;

import java.time.LocalDate;

/**
 * This dto class to get the activity info
 */
@Data
public class DailyDataLogs {
    private Long logId;
    private String date;
    private double workHours;
    private double socialHours;
    private double entertainmentHours;
    private double sleepHours;
    private int stressLevel;
    private int sleepQuality;
    private int physicalActivityLevel;
    private int wellBeingScore;

    public DailyDataLogs(Long id, LocalDate logDate, double workHours, double socialHours, double entertainmentHours, double sleepHours,
                         int stressLevel, int sleepQuality, int physicalActivityLevel, int wellBeingScore){
        this.logId = id;
        this.date = logDate.toString();
        this.workHours = workHours;
        this.socialHours = socialHours;
        this.entertainmentHours = entertainmentHours;
        this.sleepHours = sleepHours;
        this.stressLevel = stressLevel;
        this.sleepQuality = sleepQuality;
        this.physicalActivityLevel = physicalActivityLevel;
        this.wellBeingScore = wellBeingScore;
    }
}
