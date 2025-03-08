package backend.MoodBuddy.external.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * This is the entity class for User Leisure Activities
 */

@Getter
@Setter
@Entity
@Table(name = "LeisureActivities")
public class UserActivities extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ActivityId")
    private Long activityId;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false)
    private UserProfile userId;

    @Column(name = "Activity")
    private String activity;
}