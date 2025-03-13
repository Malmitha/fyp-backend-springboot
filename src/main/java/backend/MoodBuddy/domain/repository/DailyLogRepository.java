package backend.MoodBuddy.domain.repository;

import backend.MoodBuddy.domain.dto.getdailylogs.DailyDataLogs;
import backend.MoodBuddy.domain.dto.getprofile.DailyData;
import backend.MoodBuddy.external.entities.DailyDataLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyLogRepository extends JpaRepository<DailyDataLog, Long> {

    /**
     * Get all logs by user ID
     * @param userId
     * @return
     */
    @Query("SELECT new backend.MoodBuddy.domain.dto.getdailylogs.DailyDataLogs " +
            "(d.id, d.logDate, d.workHours, d.socialHours, d.entertainmentHours, d.sleepHours, d.sleepQuality, " +
            "d.physicalActivityLevel, d.stressLevel, d.wellBeingScore) " +
            "FROM DailyDataLog d " +
            "WHERE (d.userId.userId = :userId) ")
    List<DailyDataLogs> getAllDailyDataById(
            @Param("userId") Long userId
    );

    /**
     * To get the list of logged dates
     * @param userId
     * @return
     */
    @Query("SELECT (d.logDate) FROM DailyDataLog d " +
            "WHERE (d.userId.userId = :userId) ")
    List<LocalDate> getAllLogDates(
            @Param("userId") Long userId
    );
}
