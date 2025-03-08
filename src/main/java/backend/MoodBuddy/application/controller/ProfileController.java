package backend.MoodBuddy.application.controller;

import backend.MoodBuddy.application.dto.createprofile.CreateRequest;
import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.application.dto.updateprofile.UpdateProfileRequest;
import backend.MoodBuddy.application.transformer.GetProfileTransformer;
import backend.MoodBuddy.application.transformer.ProfileTransformer;
import backend.MoodBuddy.application.transformer.ResponseEntityTransformer;
import backend.MoodBuddy.application.validator.RequestEntityValidator;
import backend.MoodBuddy.domain.dto.getprofile.GetProfileResponse;
import backend.MoodBuddy.domain.dto.profile.ProfileResponse;
import backend.MoodBuddy.domain.service.ProfileService;
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
public class ProfileController extends BaseController{

    @Autowired
    private RequestEntityValidator validator;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ResponseEntityTransformer responseEntityTransformer;
    @Autowired
    private ProfileTransformer transformer;
    @Autowired
    private GetProfileTransformer getProfileTransformer;

    /**
     * This is the controller to create the user profile
     * @param request
     * @return
     * @throws DomainException
     */

    @PostMapping(value = "/profile/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUserProfile(@Validated @RequestBody CreateRequest request) throws DomainException {
        validator.validate(request);
        ProfileResponse response = profileService.createProfile(request);
        Map trResponse = responseEntityTransformer.transform(response, transformer);
        return getResponseEntity(response.getResponseHeader().getResponseCode(), trResponse);
    }

    /**
     * This is the controller to update user profile
     * @param request
     * @return
     * @throws DomainException
     */
    @PostMapping(value = "/profile/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateProfile(@Validated @RequestBody UpdateProfileRequest request) throws DomainException {
        validator.validate(request);
        ProfileResponse response = profileService.updateProfile(request);
        Map trResponse = responseEntityTransformer.transform(response, transformer);
        return getResponseEntity(response.getResponseHeader().getResponseCode(), trResponse);
    }

    /**
     * This is the controller to get user profile
     * @param request
     * @return
     * @throws DomainException
     */
    @PostMapping(value = "/profile/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getProfile(@Validated @RequestBody GetRequest request) throws DomainException {
        validator.validate(request);
        GetProfileResponse response = profileService.getProfile(request);
        Map trResponse = responseEntityTransformer.transform(response, getProfileTransformer);
        return getResponseEntity(response.getResponseHeader().getResponseCode(), trResponse);
    }
}
