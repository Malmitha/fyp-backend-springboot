package backend.MoodBuddy.domain.dto.login;

import backend.MoodBuddy.domain.dto.ResponseHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the class for login user response
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private LoginResponseBody responseBody;
    private ResponseHeader responseHeader;
}