package backend.MoodBuddy.application.dto.createprofile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * This is the class for create user profile request body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestBody {

    @NotNull(message = "First name must not be null")
    private String firstName;

    @NotNull(message = "Last name must not be null")
    private String lastName;

    @NotNull(message = "Nick name must not be null")
    private String nickName;

    @NotNull(message = "Gender must not be null")
    @Pattern(regexp = "male|female", message = "Gender must be either 'male' or 'female'")
    private String gender;

    @NotNull(message = "Age must not be null")
    private int age;

    @NotNull(message = "Date of Birth must not be null")
    private String dateOfBirth;

    @NotNull(message = "Username must not be null")
    @Size(min = 4, message = "Username must not be empty")
    private String username;

    @NotNull(message = "Password must not be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;

    @NotNull(message = "Confirm password must not be null")
    private String confirmPassword;

    @NotNull(message = "Email must not be null")
    private String email;

    @NotNull(message = "Occupation must not be null")
    private String occupation;

    @NotNull(message = "Hobbies must not be null")
    private List<String> hobbies;

    @NotNull(message = "Activities must not be null")
    private List<String> happyActivities;

}
