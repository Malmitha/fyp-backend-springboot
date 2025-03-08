package backend.MoodBuddy.external.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This is the entity class for Daily Logs of the user
 */

@Getter
@Setter
@Entity
@Table(name = "DailyLogs")
public class DailyDataLog extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false)
    private UserProfile userId;

    @Column(name = "WorkHours")
    private double workHours;

    @Column(name = "SocialHours")
    private double socialHours;

    @Column(name = "EntertainmentHours")
    private double entertainmentHours;

    @Column(name = "SleepHours")
    private double sleepHours;

    @Column(name = "StressLevel")
    private int stressLevel;

    @Column(name = "SleepQuality")
    private int sleepQuality;

    @Column(name = "PhysicalActivityLevel")
    private int physicalActivityLevel;

    @Column(name = "WellBeingScore")
    private int wellBeingScore;

    @Column(name = "LogDate")
    private LocalDate logDate;

    @Column(name = "LogTime")
    private LocalTime logTime;
}
