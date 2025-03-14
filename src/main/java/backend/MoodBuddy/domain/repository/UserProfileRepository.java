package backend.MoodBuddy.domain.repository;

import backend.MoodBuddy.domain.dto.getprofile.Profile;
import backend.MoodBuddy.external.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /**
     * Query to find by ID to update the profile
     * @param userId
     * @return
     */
    @Query("SELECT up " +
            "FROM UserProfile up " +
            "WHERE up.userId = :userId")
    Optional<UserProfile> findById(
            @Param("userId") Long userId
    );

    /**
     * Query to get user information by user id.
     * @param userId
     * @param date
     * @return
     */
    @Query("SELECT new backend.MoodBuddy.domain.dto.getprofile.Profile " +
            "(up.userId, up.firstName, up.lastName, up.nickname, up.gender, up.email, up.occupation, up.age, up.dateOfBirth) " +
            "FROM UserProfile up " +
            "WHERE (up.userId = :userId) " +
            "AND (up.recordExpiryDate IS NULL OR up.recordExpiryDate >= :date) ")
    Optional<Profile> getProfileById(
            @Param("userId") Long userId,
            @Param("date") LocalDateTime date
    );

    /**
     * To check if the user ID exists
     * @param userId
     * @return
     */
    @Query("SELECT COUNT(*) > 0 " +
            "FROM UserProfile up " +
            "WHERE up.userId = :userId" )
    Boolean existsByUserId(
            @Param("userId") Long userId
    );
}
