package backend.MoodBuddy.domain.service;

import backend.MoodBuddy.application.dto.createprofile.CreateRequest;
import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.application.dto.updateprofile.UpdateProfileRequest;
import backend.MoodBuddy.application.dto.updateprofile.UpdateProfileRequestBody;
import backend.MoodBuddy.domain.dto.ResponseHeader;
import backend.MoodBuddy.domain.dto.getprofile.*;
import backend.MoodBuddy.domain.dto.profile.ProfileResponse;
import backend.MoodBuddy.domain.dto.profile.ProfileResponseBody;
import backend.MoodBuddy.domain.repository.*;
import backend.MoodBuddy.external.entities.*;
import backend.MoodBuddy.external.exceptions.DomainErrorCode;
import backend.MoodBuddy.external.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * This is the service class responsible for  the user profile
 */
@Service
public class ProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserHobbiesRepository userHobbiesRepository;
    @Autowired
    private UserActivityRepository userActivityRepository;
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;
    @Autowired
    private DailyLogRepository dailyLogRepository;

    /**
     * This is the service class to create user profile
     * @param request
     * @return
     * @throws DomainException
     */
    public ProfileResponse createProfile(CreateRequest request) throws DomainException {
        try {
            if (!request.getRequestBody().getPassword().equals(request.getRequestBody().getConfirmPassword())) {
                throw new DomainException(DomainErrorCode.PASSWORD_MISMATCH.getCode(), DomainErrorCode.PASSWORD_MISMATCH.getDesc());
            }

            if (userCredentialsRepository.existsByUsername(request.getRequestBody().getUsername())) {
                throw new DomainException(DomainErrorCode.USERNAME_ALREADY_EXISTS.getCode(),
                        DomainErrorCode.USERNAME_ALREADY_EXISTS.getDesc());
            }

            // Save profile to database
            UserProfile up = new UserProfile();
            up.setFirstName(request.getRequestBody().getFirstName());
            up.setLastName(request.getRequestBody().getLastName());
            up.setAge(request.getRequestBody().getAge());
            up.setGender(request.getRequestBody().getGender());
            up.setNickname(request.getRequestBody().getNickName());
            up.setOccupation(request.getRequestBody().getOccupation());
            up.setEmail(request.getRequestBody().getEmail());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            up.setDateOfBirth(LocalDate.parse(request.getRequestBody().getDateOfBirth(), formatter));
            userProfileRepository.saveAndFlush(up);
            Long userId = up.getUserId();

            // Save credentials to database
            UserCredentials uc = new UserCredentials();
            uc.setUsername(request.getRequestBody().getUsername());
            uc.setPassword(request.getRequestBody().getPassword());
            uc.setUserId(up);
            userCredentialsRepository.saveAndFlush(uc);

            // Save hobbies to database
            for (String hobby : request.getRequestBody().getHobbies()) {
                UserHobbies uh = new UserHobbies();
                uh.setUserId(up);
                uh.setHobby(hobby);
                userHobbiesRepository.saveAndFlush(uh);
            }

            // Save happy activities to database
            for (String activity : request.getRequestBody().getHappyActivities()) {
                UserActivities ua = new UserActivities();
                ua.setUserId(up);
                ua.setActivity(activity);
                userActivityRepository.saveAndFlush(ua);
            }

            ProfileResponseBody responseBody = new ProfileResponseBody(userId);
            ResponseHeader responseHeader = new ResponseHeader(request.getRequestHeader().getRequestId(),
                    LocalDateTime.now().toString(), DomainErrorCode.SUCCESS.getCode(), DomainErrorCode.SUCCESS.getDesc(),
                    "200");
            return new ProfileResponse(responseBody, responseHeader);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.USER_PROFILE_ERROR.getCode(),
                    DomainErrorCode.USER_PROFILE_ERROR.getDesc());
        }
    }

    /** This is the service class to update user profile
     *
     * @param request
     * @return
     * @throws DomainException
     */
    public ProfileResponse updateProfile(UpdateProfileRequest request) throws DomainException {
        try {
            UpdateProfileRequestBody requestBody = request.getRequestBody();
            Long userId = requestBody.getUserId();
            Optional<UserProfile> profile = userProfileRepository.findById(userId);
            if (profile.isEmpty()) {
                throw new DomainException(DomainErrorCode.DATA_NOT_FOUND.getCode(), DomainErrorCode.DATA_NOT_FOUND.getDesc());
            }
            UserProfile up = profile.get();
            if (requestBody.getNickName() != null) {
                up.setNickname(requestBody.getNickName());
            }
            if (requestBody.getOccupation() != null) {
                up.setOccupation(requestBody.getOccupation());
            }
            if (requestBody.getEmail() != null) {
                up.setEmail(requestBody.getEmail());
            }
            up.setLastUpdateDate(LocalDateTime.now());
            userProfileRepository.saveAndFlush(profile.get());

            if (requestBody.getHobbies() != null) {
                userHobbiesRepository.markHobbiesAsExpired(userId, LocalDateTime.now());
                for (String hobby : request.getRequestBody().getHobbies()) {
                    UserHobbies uh = new UserHobbies();
                    uh.setUserId(up);
                    uh.setHobby(hobby);
                    userHobbiesRepository.saveAndFlush(uh);
                }
            }
            if (requestBody.getHappyActivities() != null) {
                userActivityRepository.markActivitiesAsExpired(userId, LocalDateTime.now());
                for (String activity : request.getRequestBody().getHappyActivities()) {
                    UserActivities ua = new UserActivities();
                    ua.setUserId(up);
                    ua.setActivity(activity);
                    userActivityRepository.saveAndFlush(ua);
                }
            }

            ProfileResponseBody responseBody = new ProfileResponseBody(userId);
            ResponseHeader responseHeader = new ResponseHeader(request.getRequestHeader().getRequestId(),
                    LocalDateTime.now().toString(), DomainErrorCode.SUCCESS.getCode(), DomainErrorCode.SUCCESS.getDesc(),
                    "200");
            return new ProfileResponse(responseBody, responseHeader);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.USER_PROFILE_UPDATE_ERROR.getCode(),
                    DomainErrorCode.USER_PROFILE_UPDATE_ERROR.getDesc());
        }
    }

    /**
     * THe service class to retrieve user profile data
     * @param request
     * @return
     * @throws DomainException
     */
    public GetProfileResponse getProfile(GetRequest request) throws DomainException {
        try {
            Long userId = request.getRequestBody().getUserId();
            // Get user profile data
            Optional<Profile> userProfile = userProfileRepository.getProfileById(userId, LocalDateTime.now());
            if (userProfile.isEmpty()){
                throw new DomainException(DomainErrorCode.DATA_NOT_EXIST.getCode(), DomainErrorCode.DATA_NOT_EXIST.getDesc());
            }
            Profile profileInfo = userProfile.get();
            // Get user login information
            Optional<LoginInfo> login = userCredentialsRepository.getLoginInfoById(userId, LocalDateTime.now());
            if (login.isEmpty()){
                throw new DomainException(DomainErrorCode.DATA_NOT_EXIST.getCode(), DomainErrorCode.DATA_NOT_EXIST.getDesc());
            }
            LoginInfo loginInfo = login.get();
            // Get user hobby information
            List<Hobbies> hobbiesInfo = userHobbiesRepository.getHobbiesById(userId, LocalDateTime.now());
            if (hobbiesInfo.isEmpty()){
                throw new DomainException(DomainErrorCode.DATA_NOT_EXIST.getCode(), DomainErrorCode.DATA_NOT_EXIST.getDesc());
            }
            // Get user activity information
            List<Activities> activityInfo = userActivityRepository.getActivitiesById(userId, LocalDateTime.now());
            if (activityInfo.isEmpty()){
                throw new DomainException(DomainErrorCode.DATA_NOT_EXIST.getCode(), DomainErrorCode.DATA_NOT_EXIST.getDesc());
            }

            GetProfileResponseBody responseBody = new GetProfileResponseBody(profileInfo, loginInfo, hobbiesInfo,
                    activityInfo);
            ResponseHeader responseHeader = new ResponseHeader(request.getRequestHeader().getRequestId(),
                    LocalDateTime.now().toString(), DomainErrorCode.SUCCESS.getCode(), DomainErrorCode.SUCCESS.getDesc(),
                    "200");
            return new GetProfileResponse(responseBody, responseHeader);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.USER_PROFILE_GET_ERROR.getCode(),
                    DomainErrorCode.USER_PROFILE_GET_ERROR.getDesc());
        }
    }
}
