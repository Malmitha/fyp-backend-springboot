package backend.MoodBuddy.external.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * This is the entity class for User Hobbies
 */
@Getter
@Setter
@Entity
@Table(name = "Hobbies")
public class UserHobbies extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HobbyId")
    private Long hobbyId;

    @Column(name = "Hobby")
    private String hobby;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false)
    private UserProfile userId;
}