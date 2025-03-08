package backend.MoodBuddy.domain.service;

import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.domain.dto.ResponseHeader;
import backend.MoodBuddy.domain.dto.loghistory.LogResponse;
import backend.MoodBuddy.domain.dto.loghistory.LogResponseBody;
import backend.MoodBuddy.domain.dto.loghistory.Logs;
import backend.MoodBuddy.domain.repository.SessionLogRepository;
import backend.MoodBuddy.external.exceptions.DomainErrorCode;
import backend.MoodBuddy.external.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This is the service class responsible for getting the logs
 */
@Service
public class LogHistoryService {
    @Autowired
    private SessionLogRepository logRepository;

    /**
     * Service method to get all the logging logs
     * @param request
     * @return
     * @throws DomainException
     */
    public LogResponse getDataLog(GetRequest request) throws DomainException {
        try{
            Long userId = request.getRequestBody().getUserId();
            List<Logs> logs = logRepository.getAllLogsById(userId);
            if (logs.isEmpty()) {
                throw new DomainException(DomainErrorCode.DATA_NOT_EXIST.getCode(), DomainErrorCode.DATA_NOT_EXIST.getDesc());
            }
            LogResponseBody responseBody = new LogResponseBody(logs);
            ResponseHeader responseHeader = new ResponseHeader(request.getRequestHeader().getRequestId(),
                    LocalDateTime.now().toString(), DomainErrorCode.SUCCESS.getCode(), DomainErrorCode.SUCCESS.getDesc(),
                    "200");
            return new LogResponse(responseBody, responseHeader);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.DATA_LOG_ERROR.getCode(), DomainErrorCode.DATA_LOG_ERROR.getDesc());
        }
    }

}
