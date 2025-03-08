package backend.MoodBuddy.domain.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the class for login user response body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseBody {
    private Long userId;
    private String message;
}
