package backend.MoodBuddy.application.controller;

import backend.MoodBuddy.application.dto.login.LoginRequest;
import backend.MoodBuddy.application.dto.loginupdate.UpdateLoginRequest;
import backend.MoodBuddy.application.dto.logout.LogoutRequest;
import backend.MoodBuddy.application.transformer.LoginTransformer;
import backend.MoodBuddy.application.transformer.ProfileTransformer;
import backend.MoodBuddy.application.transformer.ResponseEntityTransformer;
import backend.MoodBuddy.application.validator.RequestEntityValidator;
import backend.MoodBuddy.domain.dto.login.LoginResponse;
import backend.MoodBuddy.domain.dto.profile.ProfileResponse;
import backend.MoodBuddy.domain.service.LoginService;
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
public class LoginController extends BaseController{

    @Autowired
    private RequestEntityValidator validator;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ResponseEntityTransformer responseEntityTransformer;
    @Autowired
    private ProfileTransformer transformer;
    @Autowired
    private LoginTransformer loginTransformer;


    /**
     * This is the controller to user login
     * @param request
     * @return
     * @throws DomainException
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> userLogin(@Validated @RequestBody LoginRequest request) throws DomainException {
        validator.validate(request);
        LoginResponse response = loginService.login(request);
        Map trResponse = responseEntityTransformer.transform(response, loginTransformer);
        return getResponseEntity(response.getResponseHeader().getResponseCode(), trResponse);
    }

    /**
     * This is the controller to user login update
     * @param request
     * @return
     * @throws DomainException
     */
    @PostMapping(value = "/login/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> userUpdateLogin(@Validated @RequestBody UpdateLoginRequest request) throws DomainException {
        validator.validate(request);
        ProfileResponse response = loginService.updateLogin(request);
        Map trResponse = responseEntityTransformer.transform(response, transformer);
        return getResponseEntity(response.getResponseHeader().getResponseCode(), trResponse);
    }

    /**
     * This is the controller to user logout
     * @param request
     * @return
     * @throws DomainException
     */
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> userLogout(@Validated @RequestBody LogoutRequest request) throws DomainException {
        validator.validate(request);
        LoginResponse response = loginService.logout(request);
        Map trResponse = responseEntityTransformer.transform(response, loginTransformer);
        return getResponseEntity(response.getResponseHeader().getResponseCode(), trResponse);
    }
}
