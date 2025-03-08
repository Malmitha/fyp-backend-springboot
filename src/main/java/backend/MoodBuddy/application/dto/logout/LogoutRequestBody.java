package backend.MoodBuddy.application.dto.logout;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * This is the class for logout request body
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequestBody {
    @NotNull(message = "User id must not be null")
    private Long userId;

    @NotNull(message = "Logged In Date must not be null")
    private String loggedInDate;

    @NotNull(message = "Logged In Time must not be null")
    private String loggedInTime;
}
