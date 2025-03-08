package backend.MoodBuddy.domain.repository;

import backend.MoodBuddy.domain.dto.getprofile.Hobbies;
import backend.MoodBuddy.external.entities.UserHobbies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserHobbiesRepository extends JpaRepository<UserHobbies, Long> {

    /**
     * This is to delete the existing hobby records to set new ones
     * @param userId
     * @param expiryDate
     */
    @Modifying
    @Transactional
    @Query("UPDATE UserHobbies u " +
            "SET u.recordExpiryDate = :expiryDate " +
            "WHERE (u.userId = :userId) " +
            "AND (u.recordExpiryDate IS NULL OR u.recordExpiryDate >= :expiryDate) ")
    void markHobbiesAsExpired(
            @Param("userId") Long userId,
            @Param("expiryDate") LocalDateTime expiryDate
    );

    /**
     * Query to get the hobby info using the user id
     * @param userId
     * @param date
     * @return
     */
    @Query("SELECT new backend.MoodBuddy.domain.dto.getprofile.Hobbies " +
            "(uh.hobbyId, uh.hobby) " +
            "FROM UserHobbies uh " +
            "WHERE (uh.userId.userId = :userId) " +
            "AND (uh.recordExpiryDate IS NULL OR uh.recordExpiryDate >= :date) ")
    List<Hobbies> getHobbiesById(
            @Param("userId") Long userId,
            @Param("date") LocalDateTime date
    );
}
