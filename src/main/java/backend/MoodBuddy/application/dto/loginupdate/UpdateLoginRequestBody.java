package backend.MoodBuddy.application.dto.loginupdate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This is the class for login request body
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLoginRequestBody {
    @NotNull(message = "User id must not be null")
    private Long userId;

    @NotNull(message = "Username must not be null")
    @Size(min = 4, message = "Username must not be empty")
    private String username;

    @NotNull(message = "Password must not be null")
    private String oldPassword;

    @NotNull(message = "Password must not be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String newPassword;

    @NotNull(message = "Confirm password must not be null")
    private String confirmPassword;
}
