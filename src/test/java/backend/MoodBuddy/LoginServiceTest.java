package backend.MoodBuddy;

import backend.MoodBuddy.application.dto.RequestHeader;
import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.application.dto.get.GetRequestBody;
import backend.MoodBuddy.application.dto.login.LoginRequest;
import backend.MoodBuddy.application.dto.login.LoginRequestBody;
import backend.MoodBuddy.application.dto.loginupdate.UpdateLoginRequest;
import backend.MoodBuddy.application.dto.loginupdate.UpdateLoginRequestBody;
import backend.MoodBuddy.application.dto.logout.LogoutRequest;
import backend.MoodBuddy.application.dto.logout.LogoutRequestBody;
import backend.MoodBuddy.domain.dto.loghistory.LogResponse;
import backend.MoodBuddy.domain.dto.loghistory.Logs;
import backend.MoodBuddy.domain.dto.login.LoginResponse;
import backend.MoodBuddy.domain.dto.profile.ProfileResponse;
import backend.MoodBuddy.domain.repository.SessionLogRepository;
import backend.MoodBuddy.domain.repository.UserCredentialsRepository;
import backend.MoodBuddy.domain.service.LogHistoryService;
import backend.MoodBuddy.domain.service.LoginService;
import backend.MoodBuddy.external.entities.SessionLogs;
import backend.MoodBuddy.external.entities.UserCredentials;
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
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @BeforeEach
    void setup(){}

    @Mock
    private UserCredentialsRepository userCredentialsRepository;
    @Mock
    private SessionLogRepository loginLogRepository;
    @InjectMocks
    private LoginService service;

    @Test
    void Login_Success() throws DomainException {
        LoginRequest request = mockLoginRequest();
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(false);
        uc.setPassword("abcd@M123");
        uc.setUserId(new UserProfile());
        when(userCredentialsRepository.existsByUsername("Julia")).thenReturn(true);
        when(userCredentialsRepository.findByUsernameAndPassword(any(), any())).thenReturn(Optional.of(uc));

        LoginResponse response = service.login(request);

        assertNotNull(response);
        assertEquals("200", response.getResponseHeader().getCode());
    }

    @Test
    void Login_IncorrectPassword() throws DomainException {
        LoginRequest request = mockLoginRequest();
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(false);
        uc.setPassword("efgh@M123");
        when(userCredentialsRepository.existsByUsername("Julia")).thenReturn(true);
        when(userCredentialsRepository.findByUsernameAndPassword(any(), any())).thenReturn(Optional.of(uc));

        assertThrows(DomainException.class, () -> service.login(request));

        assertEquals("2006", DomainErrorCode.INCORRECT_DATA.getCode());
    }

    @Test
    void Login_UserAlreadyLoggedIn() throws DomainException {
        LoginRequest request = mockLoginRequest();
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(true);
        uc.setPassword("efgh@M123");
        when(userCredentialsRepository.existsByUsername("Julia")).thenReturn(true);
        when(userCredentialsRepository.findByUsernameAndPassword(any(), any())).thenReturn(Optional.of(uc));

        assertThrows(DomainException.class, () -> service.login(request));

        assertEquals("2013", DomainErrorCode.USER_ALREADY_LOGGED_IN.getCode());
    }

    @Test
    void Login_DataNotFound() throws DomainException {
        LoginRequest request = mockLoginRequest();

        when(userCredentialsRepository.existsByUsername("Julia")).thenReturn(true);
        when(userCredentialsRepository.findByUsernameAndPassword(any(), any())).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> service.login(request));

        assertEquals("2005", DomainErrorCode.DATA_NOT_FOUND.getCode());
    }

    @Test
    void Login_UsernameNotFound() throws DomainException {
        LoginRequest request = mockLoginRequest();

        when(userCredentialsRepository.existsByUsername("Julia")).thenReturn(false);

        assertThrows(DomainException.class, () -> service.login(request));

        assertEquals("2004", DomainErrorCode.USERNAME_NOT_FOUND.getCode());
    }

    @Test
    void Login_Exception() throws DomainException {
        LoginRequest request = mockLoginRequest();

        when(userCredentialsRepository.existsByUsername("Julia")).thenReturn(true);
        when(userCredentialsRepository.findByUsernameAndPassword(any(), any())).thenThrow(RuntimeException.class);

        assertThrows(DomainException.class, () -> service.login(request));

        assertEquals("2007", DomainErrorCode.USER_LOGIN_ERROR.getCode());
    }

    @Test
    void UpdateLogin_Success() throws DomainException {
        UpdateLoginRequest request = mockUpdateLoginRequest();
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(true);
        uc.setPassword("abcd@M123");
        uc.setUsername("Julie");
        uc.setUserId(new UserProfile());
        when(userCredentialsRepository.findById(1L)).thenReturn(Optional.of(uc));

        ProfileResponse response = service.updateLogin(request);

        assertNotNull(response);
        assertEquals("200", response.getResponseHeader().getCode());
    }

    @Test
    void UpdateLogin_PasswordMismatch() throws DomainException {
        UpdateLoginRequest request = mockUpdateLoginRequest();
        request.getRequestBody().setConfirmPassword("1bsd@468");
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(true);
        uc.setPassword("abcd@M123");
        uc.setUserId(new UserProfile());
        when(userCredentialsRepository.findById(1L)).thenReturn(Optional.of(uc));
        when(userCredentialsRepository.existsByUsername(any())).thenReturn(false);

        assertThrows(DomainException.class, () -> service.updateLogin(request));
        assertEquals("2001", DomainErrorCode.PASSWORD_MISMATCH.getCode());
    }

    @Test
    void UpdateLogin_PasswordNotFound() throws DomainException {
        UpdateLoginRequest request = mockUpdateLoginRequest();
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(true);
        uc.setPassword("acbd@M243");
        uc.setUserId(new UserProfile());
        when(userCredentialsRepository.findById(1L)).thenReturn(Optional.of(uc));
        when(userCredentialsRepository.existsByUsername(any())).thenReturn(false);

        assertThrows(DomainException.class, () -> service.updateLogin(request));
        assertEquals("2009", DomainErrorCode.PASSWORDS_DO_NOT_MATCH.getCode());
    }

    @Test
    void UpdateLogin_UsernameAlreadyExists() throws DomainException {
        UpdateLoginRequest request = mockUpdateLoginRequest();
        request.getRequestBody().setConfirmPassword("1bsd@468");
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(true);
        uc.setPassword("acbd@M243");
        uc.setUserId(new UserProfile());
        when(userCredentialsRepository.findById(1L)).thenReturn(Optional.of(uc));
        when(userCredentialsRepository.existsByUsername(any())).thenReturn(true);

        assertThrows(DomainException.class, () -> service.updateLogin(request));
        assertEquals("2002", DomainErrorCode.USERNAME_ALREADY_EXISTS.getCode());
    }

    @Test
    void UpdateLogin_UserNotLoggedIn() throws DomainException {
        UpdateLoginRequest request = mockUpdateLoginRequest();
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(false);
        uc.setPassword("acbd@M243");
        uc.setUserId(new UserProfile());
        when(userCredentialsRepository.findById(1L)).thenReturn(Optional.of(uc));

        assertThrows(DomainException.class, () -> service.updateLogin(request));
        assertEquals("2015", DomainErrorCode.USER_NOT_LOGGED_IN.getCode());
    }

    @Test
    void UpdateLogin_DataNotFound() throws DomainException {
        UpdateLoginRequest request = mockUpdateLoginRequest();
        when(userCredentialsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> service.updateLogin(request));
        assertEquals("2005", DomainErrorCode.DATA_NOT_FOUND.getCode());
    }

    @Test
    void UpdateLogin_Exception() throws DomainException {
        UpdateLoginRequest request = mockUpdateLoginRequest();
        when(userCredentialsRepository.findById(1L)).thenThrow(RuntimeException.class);

        assertThrows(DomainException.class, () -> service.updateLogin(request));
        assertEquals("2010", DomainErrorCode.USER_LOGIN_UPDATE_ERROR.getCode());
    }

    @Test
    void Logout_Success() throws DomainException {
        LogoutRequest request = mockLogoutRequest();
        UserProfile up = new UserProfile();
        up.setUserId(1L);
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(true);
        uc.setUserId(up);
        SessionLogs logs = new SessionLogs();
        logs.setLoggedInTime(LocalTime.now());
        when(userCredentialsRepository.findById(1L)).thenReturn(Optional.of(uc));
        when(loginLogRepository.findById(any(), any(), any())).thenReturn(Optional.of(logs));

        LoginResponse response = service.logout(request);

        assertNotNull(response);
        assertEquals("200", response.getResponseHeader().getCode());
    }

    @Test
    void Logout_SessionLogsNotFound() throws DomainException {
        LogoutRequest request = mockLogoutRequest();
        UserProfile up = new UserProfile();
        up.setUserId(1L);
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(true);
        uc.setUserId(up);
        when(userCredentialsRepository.findById(1L)).thenReturn(Optional.of(uc));
        when(loginLogRepository.findById(any(), any(), any())).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> service.logout(request));
        assertEquals("2005", DomainErrorCode.DATA_NOT_FOUND.getCode());
    }

    @Test
    void Logout_UseNotLoggedIn() throws DomainException {
        LogoutRequest request = mockLogoutRequest();
        UserProfile up = new UserProfile();
        up.setUserId(1L);
        UserCredentials uc = new UserCredentials();
        uc.setIsLoggedIn(false);
        uc.setUserId(up);
        when(userCredentialsRepository.findById(1L)).thenReturn(Optional.of(uc));

        assertThrows(DomainException.class, () -> service.logout(request));
        assertEquals("2015", DomainErrorCode.USER_NOT_LOGGED_IN.getCode());
    }

    @Test
    void Logout_CredentialsNotFound() throws DomainException {
        LogoutRequest request = mockLogoutRequest();

        when(userCredentialsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> service.logout(request));
        assertEquals("2005", DomainErrorCode.DATA_NOT_FOUND.getCode());
    }

    @Test
    void Logout_Exception() throws DomainException {
        LogoutRequest request = mockLogoutRequest();

        when(userCredentialsRepository.findById(1L)).thenThrow(RuntimeException.class);

        assertThrows(DomainException.class, () -> service.logout(request));
        assertEquals("2016", DomainErrorCode.USER_LOGOUT_ERROR.getCode());
    }

    private LoginRequest mockLoginRequest(){
        LoginRequest request = new LoginRequest();
        request.setRequestBody(new LoginRequestBody());
        request.getRequestBody().setPassword("abcd@M123");
        request.getRequestBody().setUsername("Julia");

        request.setRequestHeader(new RequestHeader("1234", LocalDateTime.now().toString()));
        return request;
    }

    private UpdateLoginRequest mockUpdateLoginRequest(){
        UpdateLoginRequest request = new UpdateLoginRequest();
        request.setRequestBody(new UpdateLoginRequestBody());
        request.getRequestBody().setUserId(1L);
        request.getRequestBody().setUsername("Julie");
        request.getRequestBody().setOldPassword("abcd@M123");
        request.getRequestBody().setNewPassword("efgh@W467");
        request.getRequestBody().setConfirmPassword("efgh@W467");

        request.setRequestHeader(new RequestHeader("1234", LocalDateTime.now().toString()));
        return request;
    }

    private LogoutRequest mockLogoutRequest(){
        LogoutRequest request = new LogoutRequest();
        request.setRequestBody(new LogoutRequestBody());
        request.getRequestBody().setLoggedInDate(LocalDate.now().toString());
        request.getRequestBody().setLoggedInTime(LocalTime.now().toString());
        request.getRequestBody().setUserId(1L);

        request.setRequestHeader(new RequestHeader("1234", LocalDateTime.now().toString()));
        return request;
    }
    private Logs mockLogs(){
        return new Logs(1L, LocalDate.now(), LocalTime.now(), LocalDate.now(), LocalTime.now(), 1.0);
    }
}
