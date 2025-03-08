package backend.MoodBuddy.application.dto.get;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the class to get user profile request body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRequestBody {

    @NotNull(message = "User Id must not be null")
    private Long userId;

}
