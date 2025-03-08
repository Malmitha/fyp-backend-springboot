package backend.MoodBuddy.domain.dto.getprofile;

import lombok.Data;

/**
 * This dto class to get the login info
 */
@Data
public class LoginInfo {
    private String username;
    private String password;

    public LoginInfo(String username, String password){
        this.username = username;
        this.password = password;
    }
}
