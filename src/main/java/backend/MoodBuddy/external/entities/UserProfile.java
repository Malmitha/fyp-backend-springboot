package backend.MoodBuddy.external.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * This is the entity class for User Profile
 */

@Getter
@Setter
@Entity
@Table(name = "ProfileInfo")
public class UserProfile extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Long userId;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Nickname")
    private String nickname;

    @Column(name = "Age")
    private int age;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "Occupation")
    private String occupation;

    @Column(name = "EmailAddress")
    private String email;
}