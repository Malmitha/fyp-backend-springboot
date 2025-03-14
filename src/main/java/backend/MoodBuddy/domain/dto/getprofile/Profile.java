package backend.MoodBuddy.domain.dto.getprofile;

import lombok.Data;

import java.time.LocalDate;

/**
 * This is the dto class to get the profile details.
 */
@Data
public class Profile {
    private Long userId;
    private String firstName;
    private String lastName;
    private String nickname;
    private String gender;
    private String email;
    private String occupation;
    private int age;
    private String dateOfBirth;

    public Profile(Long userId, String firstName, String lastName, String nickname, String gender, String email,
                   String occupation, int age, LocalDate dateOfBirth) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.gender = gender;
        this.email = email;
        this.occupation = occupation;
        this.age = age;
        this.dateOfBirth = dateOfBirth.toString();
    }
}
