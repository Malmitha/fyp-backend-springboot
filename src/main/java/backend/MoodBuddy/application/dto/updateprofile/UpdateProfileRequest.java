package backend.MoodBuddy.application.dto.updateprofile;

import backend.MoodBuddy.application.dto.RequestHeader;
import backend.MoodBuddy.application.validator.RequestEntityInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * This is the class for update user profile request
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest implements RequestEntityInterface {

    @Valid
    @NotNull(message = "Request Body cannot be null")
    private UpdateProfileRequestBody requestBody;

    @Valid
    @NotNull(message = "Request Header cannot be null")
    private RequestHeader requestHeader;
}
