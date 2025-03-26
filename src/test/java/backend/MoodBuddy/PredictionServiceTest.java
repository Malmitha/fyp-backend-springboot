package backend.MoodBuddy;

import backend.MoodBuddy.application.dto.RequestHeader;
import backend.MoodBuddy.application.dto.dailylog.DailyLogRequestBody;
import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.application.dto.get.GetRequestBody;
import backend.MoodBuddy.domain.dto.loghistory.LogResponse;
import backend.MoodBuddy.domain.dto.loghistory.Logs;
import backend.MoodBuddy.domain.repository.SessionLogRepository;
import backend.MoodBuddy.domain.service.LogHistoryService;
import backend.MoodBuddy.domain.service.PredictionService;
import backend.MoodBuddy.external.MLModelClient;
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
import java.time.LocalTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PredictionServiceTest {
    @BeforeEach
    void setup(){}

    @Mock
    private MLModelClient mlModelClient;
    @InjectMocks
    private PredictionService service;

    @Test
    void PredictMood_Success() throws Exception {
        DailyLogRequestBody requestBody = mockDailyLogRequestBody();
        when(mlModelClient.getPrediction(any())).thenReturn(5);

        int result = service.predictMood(requestBody, "Software Engineer", 25, "male");

        assertEquals(5, result);
    }

    @Test
    void PredictMood_Exception() throws Exception {
        DailyLogRequestBody requestBody = mockDailyLogRequestBody();
        when(mlModelClient.getPrediction(any())).thenThrow(RuntimeException.class);

        assertThrows(DomainException.class, () -> service.predictMood(requestBody, "Software Engineer", 25, "male"));

        assertEquals("2021", DomainErrorCode.CONNECTION_ERROR.getCode());
    }

    private DailyLogRequestBody mockDailyLogRequestBody(){
        DailyLogRequestBody requestBody = new DailyLogRequestBody();
        requestBody.setUserId(1L);
        requestBody.setSocialHours(6.0);
        requestBody.setSleepHours(6.0);
        requestBody.setEntertainmentHours(6.0);
        requestBody.setWorkHours(6.0);
        requestBody.setSleepQuality(5);
        requestBody.setStressLevel(2);
        requestBody.setPhysicalActivityLevel(5);
        requestBody.setWellBeingScore(5);

        return requestBody;
    }

}
