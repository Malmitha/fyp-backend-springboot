package backend.MoodBuddy.domain.dto.profile;

import backend.MoodBuddy.domain.dto.ResponseHeader;
import lombok.*;

/**
 * This is the class for create user profile response
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    private ProfileResponseBody responseBody;
    private ResponseHeader responseHeader;
}