package backend.MoodBuddy.application.controller;

import backend.MoodBuddy.application.dto.dailylog.DailyLogRequest;
import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.application.transformer.DailyLogTransformer;
import backend.MoodBuddy.application.transformer.GetDailyLogTransformer;
import backend.MoodBuddy.application.transformer.ResponseEntityTransformer;
import backend.MoodBuddy.application.validator.RequestEntityValidator;
import backend.MoodBuddy.domain.dto.dailylog.DailyLogResponse;
import backend.MoodBuddy.domain.dto.getdailylogs.GetDailyLogResponse;
import backend.MoodBuddy.domain.service.DailyLogService;
import backend.MoodBuddy.external.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${base-url.context}")
@CrossOrigin(origins = "*")
public class DailyLogController extends BaseController{

    @Autowired
    private RequestEntityValidator validator;
    @Autowired
    private DailyLogService dailyLogService;
    @Autowired
    private ResponseEntityTransformer responseEntityTransformer;
    @Autowired
    private DailyLogTransformer dailyLogTransformer;
    @Autowired
    private GetDailyLogTransformer getDailyLogTransformer;


    /**
     * This is the controller to user data log
     * @param request
     * @return
     * @throws DomainException
     */
    @PostMapping(value = "/daily/log", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createDailyLog(@Validated @RequestBody DailyLogRequest request) throws DomainException {
        validator.validate(request);
        DailyLogResponse response = dailyLogService.createDataLog(request);
        Map trResponse = responseEntityTransformer.transform(response, dailyLogTransformer);
        return getResponseEntity(response.getResponseHeader().getResponseCode(), trResponse);
    }

    /**
     * This is the controller to get all user data logs
     * @param request
     * @return
     * @throws DomainException
     */
    @PostMapping(value = "/daily/log/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDailyLog(@Validated @RequestBody GetRequest request) throws DomainException {
        validator.validate(request);
        GetDailyLogResponse response = dailyLogService.getDailyLogs(request);
        Map trResponse = responseEntityTransformer.transform(response, getDailyLogTransformer);
        return getResponseEntity(response.getResponseHeader().getResponseCode(), trResponse);
    }
}
