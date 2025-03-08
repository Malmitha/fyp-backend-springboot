package backend.MoodBuddy.external.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This is the entity class for User Credentials
 */

@Getter
@Setter
@Entity
@Table(name = "Credentials")
public class UserCredentials extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false)
    private UserProfile userId;

    @Column(name = "UserName")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "IsLoggedIn")
    private Boolean isLoggedIn = false;
}