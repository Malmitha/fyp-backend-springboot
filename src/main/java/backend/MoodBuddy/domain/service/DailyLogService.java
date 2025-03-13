package backend.MoodBuddy.domain.service;

import backend.MoodBuddy.application.dto.dailylog.DailyLogRequest;
import backend.MoodBuddy.application.dto.dailylog.DailyLogRequestBody;
import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.domain.dto.ResponseHeader;
import backend.MoodBuddy.domain.dto.dailylog.DailyLogResponse;
import backend.MoodBuddy.domain.dto.dailylog.DailyLogResponseBody;
import backend.MoodBuddy.domain.dto.dailylog.MoodLogs;
import backend.MoodBuddy.domain.dto.getdailylogs.AllMoodLogs;
import backend.MoodBuddy.domain.dto.getdailylogs.DailyDataLogs;
import backend.MoodBuddy.domain.dto.getdailylogs.GetDailyLogResponse;
import backend.MoodBuddy.domain.dto.getdailylogs.GetDailyLogResponseBody;
import backend.MoodBuddy.domain.repository.DailyLogRepository;
import backend.MoodBuddy.domain.repository.MoodLogRepository;
import backend.MoodBuddy.domain.repository.UserProfileRepository;
import backend.MoodBuddy.external.entities.DailyDataLog;
import backend.MoodBuddy.external.entities.MoodLog;
import backend.MoodBuddy.external.entities.UserProfile;
import backend.MoodBuddy.external.exceptions.DomainErrorCode;
import backend.MoodBuddy.external.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * This is the service class responsible for creating the daily logs
 */
@Service
public class DailyLogService {
    @Autowired
    private DailyLogRepository dailyLogRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private MoodLogRepository moodLogRepository;
    @Autowired
    private PredictionService predictionService;

    public DailyLogResponse createDataLog(DailyLogRequest request) throws DomainException {
        try {
            DailyLogRequestBody body = request.getRequestBody();
            Long userId = body.getUserId();
            int noOfLogs = moodLogRepository.countMoodLogsForToday(userId, LocalDate.now());
            if (noOfLogs > 0) {
                throw new DomainException(DomainErrorCode.PREDICTION_ERROR.getCode(), DomainErrorCode.PREDICTION_ERROR.getDesc());
            }
            double totalHours = body.getWorkHours() + body.getSocialHours() + body.getEntertainmentHours() + body.getSleepHours();
            if (totalHours != 24) {
                throw new DomainException(DomainErrorCode.INVALID_TIME.getCode(),
                        String.format("%s Current total: %.2f", DomainErrorCode.INVALID_TIME.getDesc(), totalHours));
            }
            Optional<UserProfile> profile = userProfileRepository.findById(userId);
            if (profile.isEmpty()) {
                throw new DomainException(DomainErrorCode.DATA_NOT_FOUND.getCode(), DomainErrorCode.DATA_NOT_FOUND.getDesc());
            }
            UserProfile userProfile = profile.get();
            DailyDataLog data = new DailyDataLog();

            data.setUserId(userProfile);
            data.setWorkHours(body.getWorkHours());
            data.setSocialHours(body.getSocialHours());
            data.setEntertainmentHours(body.getEntertainmentHours());
            data.setSleepHours(body.getSleepHours());
            data.setStressLevel(body.getStressLevel());
            data.setSleepQuality(body.getSleepQuality());
            data.setPhysicalActivityLevel(body.getPhysicalActivityLevel());
            data.setWellBeingScore(body.getWellBeingScore());
            data.setLogDate(LocalDate.now());
            data.setLogTime(LocalTime.now());


            int predictedMood = predictionService.predictMood(body, userProfile.getOccupation(), userProfile.getAge(), userProfile.getGender());

            MoodLogs mood = createMoodLog(userId, predictedMood);
            DailyDataLog dailyData = dailyLogRepository.saveAndFlush(data);
            Long id = dailyData.getId();
            String message = "User data added successfully";
            DailyLogResponseBody responseBody = new DailyLogResponseBody(id, mood, message);
            ResponseHeader responseHeader = new ResponseHeader(request.getRequestHeader().getRequestId(),
                    LocalDateTime.now().toString(), DomainErrorCode.SUCCESS.getCode(), DomainErrorCode.SUCCESS.getDesc(),
                    "200");
            return new DailyLogResponse(responseBody, responseHeader);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.DATA_LOG_ERROR.getCode(), DomainErrorCode.DATA_LOG_ERROR.getDesc());
        }
    }

    /**
     * Service method to get all the daily logs
     * @param request
     * @return
     * @throws DomainException
     */
    public GetDailyLogResponse getDailyLogs (GetRequest request) throws DomainException {
        try{
            Long userId = request.getRequestBody().getUserId();
            boolean userExists = userProfileRepository.existsByUserId(userId);
            if (!userExists){
                throw new DomainException(DomainErrorCode.USERID_NOT_AVAILABLE.getCode(),
                        DomainErrorCode.USERID_NOT_AVAILABLE.getDesc());
            }
            List<LocalDate> dates = dailyLogRepository.getAllLogDates(userId);

            List<DailyDataLogs> logs = dailyLogRepository.getAllDailyDataById(userId);

            List<AllMoodLogs> moodLogs = moodLogRepository.getAllMoodLogsById(userId);

            GetDailyLogResponseBody responseBody = new GetDailyLogResponseBody(dates, logs, moodLogs);
            ResponseHeader responseHeader = new ResponseHeader(request.getRequestHeader().getRequestId(),
                    LocalDateTime.now().toString(), DomainErrorCode.SUCCESS.getCode(), DomainErrorCode.SUCCESS.getDesc(),
                    "200");
            return new GetDailyLogResponse(responseBody, responseHeader);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.DATA_LOG_ERROR.getCode(), DomainErrorCode.DATA_LOG_ERROR.getDesc());
        }
    }

    /**
     * Method to create the mood log entry and return the log id
     * @param userId
     * @param prediction
     * @return
     */
    private MoodLogs createMoodLog(Long userId, int prediction){
        String category = null;
        String type = null;
        switch (prediction) {
            case 1 -> category = "Stressed";
            case 2 -> category = "Very Sad";
            case 3 -> category = "Sad";
            case 4 -> category = "Down";
            case 5 -> category = "Unsure";
            case 6 -> category = "Okay";
            case 7 -> category = "Good";
            case 8 -> category = "Happy";
            case 9 -> category = "Excited";
            case 10 -> category = "Amazing";
        }

        if (prediction == 1 || prediction == 2 || prediction == 3 || prediction == 4 ){
            type = "Negative";
        } else if (prediction == 5 || prediction == 6 ) {
            type = "Neutral";
        } else {
            type = "Positive";
        }

        MoodLog ml = new MoodLog();
        ml.setUserId(userId);
        ml.setRating(prediction);
        ml.setCategory(category);
        ml.setType(type);
        ml.setLogDate(LocalDate.now());
        ml.setLogTime(LocalTime.now());
        MoodLog log = moodLogRepository.saveAndFlush(ml);

        MoodLogs logs = new MoodLogs(log.getLogId(),log.getRating(), log.getCategory(), log.getType(), log.getLogDate(),
                log.getLogTime());
        return logs;
    }
}
