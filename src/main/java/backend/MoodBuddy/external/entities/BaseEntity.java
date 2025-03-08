package backend.MoodBuddy.external.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * This is the entity class for created date, update date and the record expiry date
 */

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "CreatedDate", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "LastUpdatedDate")
    private LocalDateTime lastUpdateDate;

    @Column(name = "RecordExpiryDate")
    private LocalDateTime recordExpiryDate;

    @PrePersist
    void onCreate() {
        createdDate = LocalDateTime.now();
        lastUpdateDate = createdDate;
    }
}