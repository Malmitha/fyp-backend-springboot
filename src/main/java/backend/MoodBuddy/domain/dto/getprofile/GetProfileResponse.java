package backend.MoodBuddy.domain.dto.getprofile;

import backend.MoodBuddy.domain.dto.ResponseHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the class for get user profile response
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProfileResponse {
    private GetProfileResponseBody responseBody;
    private ResponseHeader responseHeader;
}