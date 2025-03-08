package backend.MoodBuddy.application.dto.logout;


import backend.MoodBuddy.application.dto.RequestHeader;
import backend.MoodBuddy.application.validator.RequestEntityInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This is the class for logout request
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest implements RequestEntityInterface {
    @Valid
    @NotNull(message = "Request Body must not be null")
    private LogoutRequestBody requestBody;

    @Valid
    @NotNull(message = "Request Header must not be null")
    private RequestHeader requestHeader;
}
