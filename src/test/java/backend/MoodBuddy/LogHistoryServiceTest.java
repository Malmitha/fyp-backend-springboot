package backend.MoodBuddy;

import backend.MoodBuddy.application.dto.RequestHeader;
import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.application.dto.get.GetRequestBody;
import backend.MoodBuddy.domain.dto.getdailylogs.GetDailyLogResponse;
import backend.MoodBuddy.domain.dto.loghistory.LogResponse;
import backend.MoodBuddy.domain.dto.loghistory.Logs;
import backend.MoodBuddy.domain.repository.SessionLogRepository;
import backend.MoodBuddy.domain.service.LogHistoryService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LogHistoryServiceTest {
    @BeforeEach
    void setup(){}

    @Mock
    private SessionLogRepository logRepository;
    @InjectMocks
    private LogHistoryService service;

    @Test
    void GetDataLog_Success() throws DomainException {
        GetRequest request = mockGetRequest();
        when(logRepository.getAllLogsById(1L)).thenReturn(Collections.singletonList(mockLogs()));

        LogResponse response = service.getDataLog(request);

        assertNotNull(response);
        assertEquals("200", response.getResponseHeader().getCode());
    }

    @Test
    void GetDataLog_DataNotExist() throws DomainException {
        GetRequest request = mockGetRequest();
        when(logRepository.getAllLogsById(1L)).thenReturn(Collections.emptyList());

        assertThrows(DomainException.class, () -> service.getDataLog(request));

        assertEquals("2011", DomainErrorCode.DATA_NOT_EXIST.getCode());
    }

    @Test
    void GetDataLog_Exception() throws DomainException {
        GetRequest request = mockGetRequest();
        when(logRepository.getAllLogsById(1L)).thenThrow(RuntimeException.class);

        assertThrows(DomainException.class, () -> service.getDataLog(request));

        assertEquals("2018", DomainErrorCode.DATA_LOG_ERROR.getCode());
    }

    private GetRequest mockGetRequest(){
        GetRequest request = new GetRequest();
        request.setRequestBody(new GetRequestBody(1L));

        request.setRequestHeader(new RequestHeader("1234", LocalDateTime.now().toString()));
        return request;
    }

    private Logs mockLogs(){
        return new Logs(1L, LocalDate.now(), LocalTime.now(), LocalDate.now(), LocalTime.now(), 1.0);
    }
}
