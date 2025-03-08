package backend.MoodBuddy.domain.dto.getprofile;

import lombok.Data;

/**
 * This dto class to get the activity info
 */
@Data
public class DailyData {
    private Long logId;
    private double workHours;
    private double socialHours;
    private double entertainmentHours;
    private double sleepHours;
    private int stressLevel;
    private int sleepQuality;
    private int physicalActivityLevel;
    private int wellBeingScore;

    public DailyData(Long id, double workHours, double socialHours, double entertainmentHours, double sleepHours,
                     int stressLevel, int sleepQuality, int physicalActivityLevel, int wellBeingScore){
        this.logId = id;
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
