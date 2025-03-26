package backend.MoodBuddy;

import backend.MoodBuddy.application.dto.RequestHeader;
import backend.MoodBuddy.application.dto.createprofile.CreateRequest;
import backend.MoodBuddy.application.dto.createprofile.CreateRequestBody;
import backend.MoodBuddy.application.dto.get.GetRequest;
import backend.MoodBuddy.application.dto.get.GetRequestBody;
import backend.MoodBuddy.application.dto.updateprofile.UpdateProfileRequest;
import backend.MoodBuddy.application.dto.updateprofile.UpdateProfileRequestBody;
import backend.MoodBuddy.domain.dto.getprofile.*;
import backend.MoodBuddy.domain.dto.profile.ProfileResponse;
import backend.MoodBuddy.domain.repository.*;
import backend.MoodBuddy.domain.service.ProfileService;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {
    @BeforeEach
    void setup(){}

    @Mock
    private UserProfileRepository userProfileRepository;
    @Mock
    private UserHobbiesRepository userHobbiesRepository;
    @Mock
    private UserActivityRepository userActivityRepository;
    @Mock
    private UserCredentialsRepository userCredentialsRepository;
    @InjectMocks
    private ProfileService service;

    @Test
    void CreateProfile_Success() throws DomainException {
        CreateRequest request = mockCreateRequest();
        UserProfile up = new UserProfile();
        up.setUserId(1L);
        when(userCredentialsRepository.existsByUsername(any())).thenReturn(false);

        ProfileResponse response = service.createProfile(request);

        assertNotNull(response);
        assertEquals("200", response.getResponseHeader().getCode());
    }

    @Test
    void CreateProfile_UsernameAlreadyExists() throws DomainException {
        CreateRequest request = mockCreateRequest();
        when(userCredentialsRepository.existsByUsername(any())).thenReturn(true);

        assertThrows(DomainException.class, () -> service.createProfile(request));
        assertEquals("2002", DomainErrorCode.USERNAME_ALREADY_EXISTS.getCode());
    }

    @Test
    void CreateProfile_PasswordMismatch() throws DomainException {
        CreateRequest request = mockCreateRequest();
        request.getRequestBody().setConfirmPassword("qwerty!1298J");

        assertThrows(DomainException.class, () -> service.createProfile(request));
        assertEquals("2001", DomainErrorCode.PASSWORD_MISMATCH.getCode());
    }

    @Test
    void CreateProfile_Exception() throws DomainException {
        CreateRequest request = mockCreateRequest();
        request.getRequestBody().setDateOfBirth("18/04/2000");

        assertThrows(DomainException.class, () -> service.createProfile(request));
        assertEquals("2003", DomainErrorCode.USER_PROFILE_ERROR.getCode());
    }

    @Test
    void UpdateProfile_Success() throws DomainException {
        UpdateProfileRequest request = mockUpdateRequest();
        UserProfile up = new UserProfile();
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(up));

        ProfileResponse response = service.updateProfile(request);

        assertNotNull(response);
        assertEquals("200", response.getResponseHeader().getCode());
    }

    @Test
    void UpdateProfile_DataNotFound() throws DomainException {
        UpdateProfileRequest request = mockUpdateRequest();
        when(userProfileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> service.updateProfile(request));
        assertEquals("2005", DomainErrorCode.DATA_NOT_FOUND.getCode());
    }

    @Test
    void UpdateProfile_Exception() throws DomainException {
        UpdateProfileRequest request = mockUpdateRequest();
        when(userProfileRepository.findById(1L)).thenThrow(RuntimeException.class);

        assertThrows(DomainException.class, () -> service.updateProfile(request));
        assertEquals("2008", DomainErrorCode.USER_PROFILE_UPDATE_ERROR.getCode());
    }

    @Test
    void GetProfile_Success() throws DomainException {
        GetRequest request = mockGetRequest();
        Profile profile = mockProfile();
        LoginInfo credentials = mockLoginInfo();
        Hobbies hobby = mockHobbies();
        Activities activity = mockActivities();
        when(userProfileRepository.getProfileById(eq(1L), any(LocalDateTime.class))).thenReturn(Optional.of(profile));
        when(userCredentialsRepository.getLoginInfoById(eq(1L), any(LocalDateTime.class))).thenReturn(Optional.of(credentials));
        when(userHobbiesRepository.getHobbiesById(eq(1L), any(LocalDateTime.class))).thenReturn(Collections.singletonList(hobby));
        when(userActivityRepository.getActivitiesById(eq(1L), any(LocalDateTime.class))).thenReturn(Collections.singletonList(activity));

        GetProfileResponse response = service.getProfile(request);

        assertNotNull(response);
        assertEquals("200", response.getResponseHeader().getCode());
    }

    @Test
    void GetProfile_ActivityDataNotPresent() throws DomainException {
        GetRequest request = mockGetRequest();
        Profile profile = mockProfile();
        LoginInfo credentials = mockLoginInfo();
        Hobbies hobby = mockHobbies();
        when(userProfileRepository.getProfileById(eq(1L), any(LocalDateTime.class))).thenReturn(Optional.of(profile));
        when(userCredentialsRepository.getLoginInfoById(eq(1L), any(LocalDateTime.class))).thenReturn(Optional.of(credentials));
        when(userHobbiesRepository.getHobbiesById(eq(1L), any(LocalDateTime.class))).thenReturn(Collections.singletonList(hobby));
        when(userActivityRepository.getActivitiesById(eq(1L), any(LocalDateTime.class))).thenReturn(Collections.emptyList());

        assertThrows(DomainException.class, () -> service.getProfile(request));
        assertEquals("2011", DomainErrorCode.DATA_NOT_EXIST.getCode());
    }

    @Test
    void GetProfile_HobbyDataNotPresent() throws DomainException {
        GetRequest request = mockGetRequest();
        Profile profile = mockProfile();
        LoginInfo credentials = mockLoginInfo();
        when(userProfileRepository.getProfileById(eq(1L), any(LocalDateTime.class))).thenReturn(Optional.of(profile));
        when(userCredentialsRepository.getLoginInfoById(eq(1L), any(LocalDateTime.class))).thenReturn(Optional.of(credentials));
        when(userHobbiesRepository.getHobbiesById(eq(1L), any(LocalDateTime.class))).thenReturn(Collections.emptyList());

        assertThrows(DomainException.class, () -> service.getProfile(request));
        assertEquals("2011", DomainErrorCode.DATA_NOT_EXIST.getCode());
    }

    @Test
    void GetProfile_LoginInfoDataNotPresent() throws DomainException {
        GetRequest request = mockGetRequest();
        Profile profile = mockProfile();
        when(userProfileRepository.getProfileById(eq(1L), any(LocalDateTime.class))).thenReturn(Optional.of(profile));
        when(userCredentialsRepository.getLoginInfoById(eq(1L), any(LocalDateTime.class))).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> service.getProfile(request));
        assertEquals("2011", DomainErrorCode.DATA_NOT_EXIST.getCode());
    }

    @Test
    void GetProfile_UserDataNotPresent() throws DomainException {
        GetRequest request = mockGetRequest();
        when(userProfileRepository.getProfileById(eq(1L), any(LocalDateTime.class))).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> service.getProfile(request));
        assertEquals("2011", DomainErrorCode.DATA_NOT_EXIST.getCode());
    }

    @Test
    void GetProfile_Exception() throws DomainException {
        GetRequest request = mockGetRequest();
        when(userProfileRepository.getProfileById(eq(1L), any(LocalDateTime.class))).thenThrow(RuntimeException.class);

        assertThrows(DomainException.class, () -> service.getProfile(request));
        assertEquals("2012", DomainErrorCode.USER_PROFILE_GET_ERROR.getCode());
    }

    private CreateRequest mockCreateRequest(){
        CreateRequest request = new CreateRequest();
        request.setRequestBody(new CreateRequestBody());
        request.getRequestBody().setAge(24);
        request.getRequestBody().setGender("Male");
        request.getRequestBody().setEmail("abc@example.com");
        request.getRequestBody().setFirstName("Joe");
        request.getRequestBody().setLastName("Brown");
        request.getRequestBody().setUsername("JoeB");
        request.getRequestBody().setDateOfBirth("12-06-2000");
        request.getRequestBody().setPassword("qwerty!J1289");
        request.getRequestBody().setConfirmPassword("qwerty!J1289");
        request.getRequestBody().setNickName("JoeB");
        request.getRequestBody().setOccupation("Doctor");
        List<String> hobbies = List.of("Reading", "Gaming", "Cycling");
        List<String> activities = List.of("Gardening", "Going out with friends", "Cycling");
        request.getRequestBody().setHobbies(hobbies);
        request.getRequestBody().setHappyActivities(activities);

        request.setRequestHeader(new RequestHeader("1234", LocalDateTime.now().toString()));
        return request;
    }

    private UpdateProfileRequest mockUpdateRequest(){
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setRequestBody(new UpdateProfileRequestBody());
        request.getRequestBody().setEmail("abc@example.com");
        request.getRequestBody().setUserId(1L);
        request.getRequestBody().setOccupation("Doctor");
        request.getRequestBody().setNickName("JoeB");
        List<String> hobbies = List.of("Reading", "Gaming", "Cycling");
        List<String> activities = List.of("Gardening", "Going out with friends", "Cycling");
        request.getRequestBody().setHappyActivities(activities);
        request.getRequestBody().setHobbies(hobbies);

        request.setRequestHeader(new RequestHeader("1234", LocalDateTime.now().toString()));
        return request;
    }

    private GetRequest mockGetRequest(){
        GetRequest request = new GetRequest();
        request.setRequestBody(new GetRequestBody(1L));

        request.setRequestHeader(new RequestHeader("1234", LocalDateTime.now().toString()));
        return request;
    }

    private Profile mockProfile(){
        return new Profile(1L, "", "", "", "", "", "",
                24, LocalDate.now());
    }
    private LoginInfo mockLoginInfo(){
        return new LoginInfo("", "");
    }
    private Hobbies mockHobbies(){
        return new Hobbies(1L, "");
    }
    private Activities mockActivities(){
        return new Activities(1L, "");
    }
}
