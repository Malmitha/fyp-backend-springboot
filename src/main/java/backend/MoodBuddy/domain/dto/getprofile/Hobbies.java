package backend.MoodBuddy.domain.dto.getprofile;

import lombok.Data;

/**
 * This dto class to get the hobby info
 */
@Data
public class Hobbies {
    private Long hobbyId;
    private String hobby;

    public Hobbies(Long hobbyId, String hobby){
        this.hobbyId = hobbyId;
        this.hobby = hobby;
    }
}
