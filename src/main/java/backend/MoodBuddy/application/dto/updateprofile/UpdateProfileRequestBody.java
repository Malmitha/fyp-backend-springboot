package backend.MoodBuddy.application.dto.updateprofile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This is the class for update user profile request body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequestBody {

    @NotNull(message = "User id must not be null")
    private Long userId;

    private String nickName;

    private String email;

    private String occupation;

    private List<String> hobbies;

    private List<String> happyActivities;

}
