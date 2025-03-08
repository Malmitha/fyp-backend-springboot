package backend.MoodBuddy.domain.dto.getprofile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This is the class for get user profile response body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProfileResponseBody {
    private Profile profile;
    private LoginInfo credentials;
    private List<Hobbies> hobbies;
    private List<Activities> activities;
}
