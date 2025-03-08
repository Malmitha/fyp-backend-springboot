package backend.MoodBuddy.application.dto.login;


import backend.MoodBuddy.application.dto.RequestHeader;
import backend.MoodBuddy.application.validator.RequestEntityInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This is the class for login request
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements RequestEntityInterface {
    @Valid
    @NotNull(message = "Request Body must not be null")
    private LoginRequestBody requestBody;

    @Valid
    @NotNull(message = "Request Header must not be null")
    private RequestHeader requestHeader;
}
