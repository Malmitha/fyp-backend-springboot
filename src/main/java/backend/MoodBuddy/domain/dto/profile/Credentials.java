package backend.MoodBuddy.domain.dto.profile;

import lombok.Data;

/**
 * This is a dto class to get the credential data
 */

@Data
public class Credentials {
    private String username;
    private String password;
    private Long userId;

    public Credentials(String username, String password, Long userId){
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public Credentials(String username, String password){
        this.username = username;
        this.password = password;
    }
}
