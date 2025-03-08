package backend.MoodBuddy.application.controller;

import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.application.transformer.LogHistoryTransformer;
import backend.MoodBuddy.application.transformer.ResponseEntityTransformer;
import backend.MoodBuddy.application.validator.RequestEntityValidator;
import backend.MoodBuddy.domain.dto.loghistory.LogResponse;
import backend.MoodBuddy.domain.service.LogHistoryService;
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
public class LogHistoryController extends BaseController{

    @Autowired
    private RequestEntityValidator validator;
    @Autowired
    private LogHistoryService logHistoryService;
    @Autowired
    private ResponseEntityTransformer responseEntityTransformer;
    @Autowired
    private LogHistoryTransformer logHistoryTransformer;


    /**
     * This is the controller to user data log
     * @param request
     * @return
     * @throws DomainException
     */
    @PostMapping(value = "/log/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLogHistory(@Validated @RequestBody GetRequest request) throws DomainException {
        validator.validate(request);
        LogResponse response = logHistoryService.getDataLog(request);
        Map trResponse = responseEntityTransformer.transform(response, logHistoryTransformer);
        return getResponseEntity(response.getResponseHeader().getResponseCode(), trResponse);
    }

}
