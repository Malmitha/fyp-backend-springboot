package backend.MoodBuddy.external.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This is the entity class for User Login logs
 */

@Getter
@Setter
@Entity
@Table(name = "SessionLogs")
public class SessionLogs extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LogId")
    private Long id;

    @Column(name = "UserId", nullable = false)
    private Long userId;

    @Column(name = "LoggedInDate")
    private LocalDate loggedInDate;

    @Column(name = "LoggedInTime")
    private LocalTime loggedInTime;

    @Column(name = "LoggedOutDate")
    private LocalDate loggedOutDate;

    @Column(name = "LoggedOutTime")
    private LocalTime loggedOutTime;

    @Column(name = "SessionDurationHours")
    private double sessionDurationHours;
}