package backend.MoodBuddy.application.dto.login;

import jakarta.validation.constraints.NotNull;
import lombok.*;


/**
 * This is the class for login request body
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestBody {
    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Password must not be null")
    private String password;
}
