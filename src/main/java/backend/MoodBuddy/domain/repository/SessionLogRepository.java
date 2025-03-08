package backend.MoodBuddy.domain.repository;

import backend.MoodBuddy.domain.dto.loghistory.Logs;
import backend.MoodBuddy.external.entities.SessionLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionLogRepository extends JpaRepository<SessionLogs, Long> {
    /**
     * Query to get the record by userId
     * @param userId
     * @return
     */
    @Query("SELECT l " +
            "FROM SessionLogs l " +
            "WHERE (l.userId = :userId) " +
            "AND (l.loggedInDate = :date) " +
            "AND (l.loggedInTime = :time)")
    Optional<SessionLogs> findById(
            @Param("userId") Long userId,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time
    );

    /**
     * Query to get all the user logs
     * @param userId
     * @return
     */
    @Query("SELECT new backend.MoodBuddy.domain.dto.loghistory.Logs " +
            "(l.id, l.loggedInDate, l.loggedInTime, l.loggedOutDate, l.loggedOutTime, l.sessionDurationHours) " +
            "FROM SessionLogs l " +
            "WHERE (l.userId = :userId) ")
    List<Logs> getAllLogsById(
            @Param("userId") Long userId
    );
}
