package backend.MoodBuddy.domain.repository;

import backend.MoodBuddy.domain.dto.getprofile.Activities;
import backend.MoodBuddy.domain.dto.getprofile.Hobbies;
import backend.MoodBuddy.external.entities.UserActivities;
import backend.MoodBuddy.external.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivities, Long> {

    /**
     * This is to delete the existing activities records to set new ones
     * @param userId
     * @param expiryDate
     */
    @Modifying
    @Transactional
    @Query("UPDATE UserActivities a " +
            "SET a.recordExpiryDate = :expiryDate " +
            "WHERE (a.userId.userId = :userId) " +
            "AND (a.recordExpiryDate IS NULL OR a.recordExpiryDate >= :expiryDate) ")
    void markActivitiesAsExpired(
            @Param("userId") Long userId,
            @Param("expiryDate") LocalDateTime expiryDate
    );

    /**
     * Query to get the user activities
     * @param userId
     * @param date
     * @return
     */
    @Query("SELECT new backend.MoodBuddy.domain.dto.getprofile.Activities " +
            "(ua.activityId, ua.activity) " +
            "FROM UserActivities ua " +
            "WHERE (ua.userId.userId = :userId) " +
            "AND (ua.recordExpiryDate IS NULL OR ua.recordExpiryDate >= :date) ")
    List<Activities> getActivitiesById(
            @Param("userId") Long userId,
            @Param("date") LocalDateTime date
    );
}
