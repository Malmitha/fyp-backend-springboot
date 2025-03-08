package backend.MoodBuddy.domain.dto.getprofile;

import lombok.Data;

/**
 * This dto class to get the activity info
 */
@Data
public class Activities {
    private Long activityId;
    private String activity;

    public Activities(Long activityId, String activity){
        this.activityId = activityId;
        this.activity = activity;
    }
}
