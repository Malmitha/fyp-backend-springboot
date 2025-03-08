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
     * Query to get user data by id and date
     * @param userId
     * @param date
     * @return
     */
    @Query("SELECT new backend.MoodBuddy.domain.dto.getprofile.DailyData " +
            "(d.id, d.workHours, d.socialHours, d.entertainmentHours, d.sleepHours, d.sleepQuality, " +
            "d.physicalActivityLevel, d.stressLevel, d.wellBeingScore) " +
            "FROM DailyDataLog d " +
            "WHERE (d.userId.userId = :userId) " +
            "AND (d.logDate = :date) ")
    Optional<DailyData> getDailyDataById(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );

    @Query("SELECT new backend.MoodBuddy.domain.dto.getdailylogs.DailyDataLogs " +
            "(d.id, d.logDate, d.workHours, d.socialHours, d.entertainmentHours, d.sleepHours, d.sleepQuality, " +
            "d.physicalActivityLevel, d.stressLevel, d.wellBeingScore) " +
            "FROM DailyDataLog d " +
            "WHERE (d.userId.userId = :userId) ")
    List<DailyDataLogs> getAllDailyDataById(
            @Param("userId") Long userId
    );
}
