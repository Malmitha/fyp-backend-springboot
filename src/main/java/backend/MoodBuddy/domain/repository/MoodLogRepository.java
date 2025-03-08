package backend.MoodBuddy.domain.repository;

import backend.MoodBuddy.domain.dto.getdailylogs.AllMoodLogs;
import backend.MoodBuddy.external.entities.MoodLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MoodLogRepository extends JpaRepository<MoodLog, Long> {
    @Query("SELECT new backend.MoodBuddy.domain.dto.getdailylogs.AllMoodLogs " +
            "(m.logId, m.rating, m.category, m.type, m.logDate, m.logTime) " +
            "FROM MoodLog m " +
            "WHERE (m.userId = :userId) ")
    List<AllMoodLogs> getAllMoodLogsById(
            @Param("userId") Long userId
    );

    @Query("SELECT COUNT(m) " +
            "FROM MoodLog m " +
            "WHERE (m.userId = :userId) " +
            "AND (m.logDate = :date) ")
    int countMoodLogsForToday(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );

}
