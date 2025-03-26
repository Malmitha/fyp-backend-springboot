package backend.MoodBuddy;

import backend.MoodBuddy.application.dto.RequestHeader;
import backend.MoodBuddy.application.dto.dailylog.DailyLogRequest;
import backend.MoodBuddy.application.dto.dailylog.DailyLogRequestBody;
import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.application.dto.get.GetRequestBody;
import backend.MoodBuddy.domain.dto.dailylog.DailyLogResponse;
import backend.MoodBuddy.domain.dto.getdailylogs.GetDailyLogResponse;
import backend.MoodBuddy.domain.repository.DailyLogRepository;
import backend.MoodBuddy.domain.repository.MoodLogRepository;
import backend.MoodBuddy.domain.repository.UserProfileRepository;
import backend.MoodBuddy.domain.service.DailyLogService;
import backend.MoodBuddy.domain.service.PredictionService;
import backend.MoodBuddy.external.entities.DailyDataLog;
import backend.MoodBuddy.external.entities.MoodLog;
import backend.MoodBuddy.external.entities.UserProfile;
import backend.MoodBuddy.external.exceptions.DomainErrorCode;
import backend.MoodBuddy.external.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DailyLogServiceTest {
    @BeforeEach
    void setup(){}

    @Mock
    private DailyLogRepository dailyLogRepository;
    @Mock
    private UserProfileRepository userProfileRepository;
    @Mock
    private MoodLogRepository moodLogRepository;
    @Mock
    private PredictionService predictionService;
    @InjectMocks
    private DailyLogService service;

    @Test
    void CreateDataLog_Success() throws DomainException {
        DailyLogRequest request = mockDailyLogRequest();
        MoodLog ml = new MoodLog();
        ml.setLogId(1L);
        DailyDataLog savedDataLog = new DailyDataLog();
        savedDataLog.setId(2L);

        when(moodLogRepository.countMoodLogsForToday(1L, LocalDate.now())).thenReturn(0);
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(mockUserProfile()));
        when(predictionService.predictMood(any(), any(), anyInt(), any())).thenReturn(7);
        when(moodLogRepository.saveAndFlush(any())).thenReturn(ml);

        when(dailyLogRepository.saveAndFlush(any())).thenReturn(savedDataLog);
        DailyLogResponse response = service.createDataLog(request);

        assertNotNull(response);
        assertEquals(2L, response.getResponseBody().getDailyDataLogId());
        assertEquals("200", response.getResponseHeader().getCode());
    }
    @Test
    void CreateDataLog_UserProfileNotFound() throws DomainException {
        DailyLogRequest request = mockDailyLogRequest();

        when(moodLogRepository.countMoodLogsForToday(1L, LocalDate.now())).thenReturn(0);
        when(userProfileRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DomainException.class, () -> service.createDataLog(request));

        assertEquals("2005", DomainErrorCode.DATA_NOT_FOUND.getCode());
    }

    @Test
    void CreateDataLog_InvalidTime() throws DomainException {
        DailyLogRequest request = mockDailyLogRequest();
        request.getRequestBody().setEntertainmentHours(4.0);

        when(moodLogRepository.countMoodLogsForToday(1L, LocalDate.now())).thenReturn(0);
        assertThrows(DomainException.class, () -> service.createDataLog(request));

        assertEquals("2017", DomainErrorCode.INVALID_TIME.getCode());
    }

    @Test
    void CreateDataLog_PredictionError() throws DomainException {
        DailyLogRequest request = mockDailyLogRequest();

        when(moodLogRepository.countMoodLogsForToday(1L, LocalDate.now())).thenReturn(1);
        assertThrows(DomainException.class, () -> service.createDataLog(request));

        assertEquals("2019", DomainErrorCode.PREDICTION_ERROR.getCode());
    }
    @Test
    void CreateDataLog_Exception() throws DomainException {
        DailyLogRequest request = mockDailyLogRequest();

        when(moodLogRepository.countMoodLogsForToday(1L, LocalDate.now())).thenReturn(0);
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(mockUserProfile()));
        when(predictionService.predictMood(any(), any(), anyInt(), any())).thenReturn(7);
        assertThrows(DomainException.class, () -> service.createDataLog(request));

        assertEquals("2018", DomainErrorCode.DATA_LOG_ERROR.getCode());
    }

    @Test
    void GetDailyLogs_Success() throws DomainException {
        GetRequest request = mockGetRequest();
        when(userProfileRepository.existsByUserId(1L)).thenReturn(true);
        when(dailyLogRepository.getAllLogDates(1L)).thenReturn(Collections.emptyList());
        when(dailyLogRepository.getAllDailyDataById(1L)).thenReturn(Collections.emptyList());
        when(moodLogRepository.getAllMoodLogsById(1L)).thenReturn(Collections.emptyList());

        GetDailyLogResponse response = service.getDailyLogs(request);

        assertNotNull(response);
        assertEquals("200", response.getResponseHeader().getCode());
    }

    @Test
    void GetDailyLogs_UserProfileNotFound() throws DomainException {
        GetRequest request = mockGetRequest();
        when(userProfileRepository.existsByUserId(1L)).thenReturn(false);

        assertThrows(DomainException.class, () -> service.getDailyLogs(request));

        assertEquals("2022", DomainErrorCode.USERID_NOT_AVAILABLE.getCode());
    }

    @Test
    void GetDailyLogs_Exception() throws DomainException {
        GetRequest request = mockGetRequest();
        when(userProfileRepository.existsByUserId(1L)).thenReturn(true);
        when(dailyLogRepository.getAllLogDates(1L)).thenThrow(RuntimeException.class);

        assertThrows(DomainException.class, () -> service.getDailyLogs(request));

        assertEquals("2018", DomainErrorCode.DATA_LOG_ERROR.getCode());
    }

    private DailyLogRequest mockDailyLogRequest(){
        DailyLogRequest request = new DailyLogRequest();
        request.setRequestBody(new DailyLogRequestBody());
        request.getRequestBody().setUserId(1L);
        request.getRequestBody().setSocialHours(6.0);
        request.getRequestBody().setSleepHours(6.0);
        request.getRequestBody().setEntertainmentHours(6.0);
        request.getRequestBody().setWorkHours(6.0);
        request.getRequestBody().setSleepQuality(5);
        request.getRequestBody().setStressLevel(2);
        request.getRequestBody().setPhysicalActivityLevel(5);
        request.getRequestBody().setWellBeingScore(5);

        request.setRequestHeader(new RequestHeader("1234", LocalDateTime.now().toString()));
        return request;
    }

    private GetRequest mockGetRequest(){
        GetRequest request = new GetRequest();
        request.setRequestBody(new GetRequestBody(1L));

        request.setRequestHeader(new RequestHeader("1234", LocalDateTime.now().toString()));
        return request;
    }

    private UserProfile mockUserProfile(){
        UserProfile up = new UserProfile();
        up.setUserId(1L);
        up.setGender("Male");
        up.setOccupation("Doctor");
        up.setAge(24);
        return up;
    }


}
