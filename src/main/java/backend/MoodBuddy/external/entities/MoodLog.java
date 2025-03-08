package backend.MoodBuddy.external.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This is the entity class for Mood Logs of the user
 */

@Getter
@Setter
@Entity
@Table(name = "MoodLogs")
public class MoodLog extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LogId")
    private Long logId;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "Rating")
    private int rating;

    @Column(name = "EmotionCategory")
    private String category;

    @Column(name = "EmotionType")   // Positive, Negative, Neutral
    private String type;

    @Column(name = "LogDate")
    private LocalDate logDate;

    @Column(name = "LogTime")
    private LocalTime logTime;
}