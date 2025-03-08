package backend.MoodBuddy.domain.service;

import backend.MoodBuddy.application.dto.login.LoginRequest;
import backend.MoodBuddy.application.dto.login.LoginRequestBody;
import backend.MoodBuddy.application.dto.loginupdate.UpdateLoginRequest;
import backend.MoodBuddy.application.dto.loginupdate.UpdateLoginRequestBody;
import backend.MoodBuddy.application.dto.logout.LogoutRequest;
import backend.MoodBuddy.domain.dto.ResponseHeader;
import backend.MoodBuddy.domain.dto.login.LoginResponse;
import backend.MoodBuddy.domain.dto.login.LoginResponseBody;
import backend.MoodBuddy.domain.dto.profile.ProfileResponse;
import backend.MoodBuddy.domain.dto.profile.ProfileResponseBody;
import backend.MoodBuddy.domain.repository.SessionLogRepository;
import backend.MoodBuddy.domain.repository.UserCredentialsRepository;
import backend.MoodBuddy.external.entities.SessionLogs;
import backend.MoodBuddy.external.entities.UserCredentials;
import backend.MoodBuddy.external.exceptions.DomainErrorCode;
import backend.MoodBuddy.external.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

/**
 * This is the service class responsible for logging the user
 */
@Service
public class LoginService {
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;
    @Autowired
    private SessionLogRepository loginLogRepository;

    public LoginResponse login(LoginRequest request) throws DomainException {
        try {
            LoginRequestBody body = request.getRequestBody();
            if (!userCredentialsRepository.existsByUsername(body.getUsername())) {
                throw new DomainException(DomainErrorCode.USERNAME_NOT_FOUND.getCode(),
                        DomainErrorCode.USERNAME_NOT_FOUND.getDesc());
            }
            Optional<UserCredentials> credentials = userCredentialsRepository.findByUsernameAndPassword(body.getUsername(),
                    body.getPassword());
            if (credentials.isEmpty()) {
                throw new DomainException(DomainErrorCode.DATA_NOT_FOUND.getCode(),
                        DomainErrorCode.DATA_NOT_FOUND.getDesc());
            }
            UserCredentials login = credentials.get();
            if (login.getIsLoggedIn()) {
                throw new DomainException(DomainErrorCode.USER_ALREADY_LOGGED_IN.getCode(),
                        DomainErrorCode.USER_ALREADY_LOGGED_IN.getDesc());
            }
            if (!login.getPassword().equals(body.getPassword())) {
                throw new DomainException(DomainErrorCode.INCORRECT_DATA.getCode(),
                        DomainErrorCode.INCORRECT_DATA.getDesc());
            }

            login.setIsLoggedIn(true);
            SessionLogs logs = new SessionLogs();
            logs.setUserId(login.getUserId().getUserId());
            logs.setLoggedInDate(LocalDate.now());
            logs.setLoggedInTime(LocalTime.now());
            loginLogRepository.saveAndFlush(logs);
            userCredentialsRepository.saveAndFlush(login);

            String message = "User successfully logged in.";
            LoginResponseBody responseBody = new LoginResponseBody(login.getUserId().getUserId(), message);
            ResponseHeader responseHeader = new ResponseHeader(request.getRequestHeader().getRequestId(),
                    LocalDateTime.now().toString(), DomainErrorCode.SUCCESS.getCode(), DomainErrorCode.SUCCESS.getDesc(),
                    "200");
            return new LoginResponse(responseBody, responseHeader);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.USER_LOGIN_ERROR.getCode(), DomainErrorCode.USER_LOGIN_ERROR.getDesc());
        }
    }

    /**
     * This is the service to update login
     * @param request
     * @return
     * @throws DomainException
     */
    public ProfileResponse updateLogin(UpdateLoginRequest request) throws DomainException {
        try {
            UpdateLoginRequestBody requestBody = request.getRequestBody();
            Optional<UserCredentials> credentials = userCredentialsRepository.findById(requestBody.getUserId());
            if (credentials.isEmpty()) {
                throw new DomainException(DomainErrorCode.DATA_NOT_FOUND.getCode(), DomainErrorCode.DATA_NOT_FOUND.getDesc());
            }
            UserCredentials uc = credentials.get();
            if (!uc.getIsLoggedIn()) {
                throw new DomainException(DomainErrorCode.USER_NOT_LOGGED_IN.getCode(),
                        DomainErrorCode.USER_NOT_LOGGED_IN.getDesc());
            }
            if (userCredentialsRepository.existsByUsername(requestBody.getUsername())) {
                throw new DomainException(DomainErrorCode.USERNAME_ALREADY_EXISTS.getCode(),
                        DomainErrorCode.USERNAME_ALREADY_EXISTS.getDesc());
            }
            if (!requestBody.getOldPassword().equals(uc.getPassword())) {
                throw new DomainException(DomainErrorCode.PASSWORDS_DO_NOT_MATCH.getCode(),
                        DomainErrorCode.PASSWORDS_DO_NOT_MATCH.getDesc());
            }
            if (!requestBody.getNewPassword().equals(requestBody.getConfirmPassword())) {
                throw new DomainException(DomainErrorCode.PASSWORD_MISMATCH.getCode(),
                        DomainErrorCode.PASSWORD_MISMATCH.getDesc());
            }
            uc.setUsername(requestBody.getUsername());
            uc.setPassword(requestBody.getNewPassword());
            uc.setLastUpdateDate(LocalDateTime.now());
            userCredentialsRepository.saveAndFlush(uc);

            ProfileResponseBody responseBody = new ProfileResponseBody(requestBody.getUserId());
            ResponseHeader responseHeader = new ResponseHeader(request.getRequestHeader().getRequestId(),
                    LocalDateTime.now().toString(), DomainErrorCode.SUCCESS.getCode(), DomainErrorCode.SUCCESS.getDesc(),
                    "200");
            return new ProfileResponse(responseBody, responseHeader);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.USER_LOGIN_UPDATE_ERROR.getCode(),
                    DomainErrorCode.USER_LOGIN_UPDATE_ERROR.getDesc());
        }
    }

    public LoginResponse logout(LogoutRequest request) throws DomainException {
        try{
            Long userId = request.getRequestBody().getUserId();
            LocalDate date = LocalDate.parse(request.getRequestBody().getLoggedInDate());
            LocalTime time = LocalTime.parse(request.getRequestBody().getLoggedInTime());
            Optional<UserCredentials> credentials = userCredentialsRepository.findById(userId);
            if (credentials.isEmpty()) {
                throw new DomainException(DomainErrorCode.DATA_NOT_FOUND.getCode(), DomainErrorCode.DATA_NOT_FOUND.getDesc());
            }
            UserCredentials logout = credentials.get();
            if (!logout.getIsLoggedIn()) {
                throw new DomainException(DomainErrorCode.USER_NOT_LOGGED_IN.getCode(),
                        DomainErrorCode.USER_NOT_LOGGED_IN.getDesc());
            }
            logout.setIsLoggedIn(false);

            Optional<SessionLogs> logs = loginLogRepository.findById(userId, date, time);
            if (logs.isEmpty()) {
                throw new DomainException(DomainErrorCode.DATA_NOT_FOUND.getCode(), DomainErrorCode.DATA_NOT_FOUND.getDesc());
            }
            SessionLogs log = logs.get();
            log.setLoggedOutDate(LocalDate.now());
            log.setLoggedOutTime(LocalTime.now());
            log.setSessionDurationHours(calculateDurationInHours(log.getLoggedInTime()));
            loginLogRepository.saveAndFlush(log);
            userCredentialsRepository.saveAndFlush(logout);

            String message = "User successfully logged out.";
            LoginResponseBody responseBody = new LoginResponseBody(logout.getUserId().getUserId(), message);
            ResponseHeader responseHeader = new ResponseHeader(request.getRequestHeader().getRequestId(),
                    LocalDateTime.now().toString(), DomainErrorCode.SUCCESS.getCode(), DomainErrorCode.SUCCESS.getDesc(),
                    "200");
            return new LoginResponse(responseBody, responseHeader);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.USER_LOGOUT_ERROR.getCode(),
                    DomainErrorCode.USER_LOGOUT_ERROR.getDesc());
        }
    }

    // Method to calculate the session duration
    public static double calculateDurationInHours(LocalTime loggedInTime) {
        // Calculate the duration between the two times
        Duration duration = Duration.between(loggedInTime, LocalTime.now());

        // Convert duration to fractional hours and round to 4 decimal places
        double durationInHours = duration.toMinutes() / 60.0;

        // Round the result to 4 decimal places
        double rounded = Math.round(durationInHours * 10000.0) / 10000.0;
        return rounded;
    }
}
