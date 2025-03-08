package backend.MoodBuddy.domain.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the class for create user profile response body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseBody {
    private Long userId;
}
