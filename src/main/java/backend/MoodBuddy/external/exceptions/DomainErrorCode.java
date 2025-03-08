package backend.MoodBuddy.external.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DomainErrorCode {

    SUCCESS("200", "Success", HttpStatus.OK),
    PASSWORD_MISMATCH("2001", "Password and Confirm Password do not match", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS("2002", "Provided username already exists", HttpStatus.BAD_REQUEST),
    USER_PROFILE_ERROR("2003", "Error occurred when creating the user profile", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_FOUND("2004", "Username doesn't exist. Please sign in first.", HttpStatus.BAD_REQUEST),
    DATA_NOT_FOUND("2005", "User data not found.", HttpStatus.BAD_REQUEST),
    INCORRECT_DATA("2006", "Incorrect username or password provided. Please check and try again", HttpStatus.BAD_REQUEST),
    USER_LOGIN_ERROR("2007", "Error occurred when logging the user in", HttpStatus.BAD_REQUEST),
    USER_PROFILE_UPDATE_ERROR("2008", "Error occurred when updating the user profile", HttpStatus.BAD_REQUEST),
    PASSWORDS_DO_NOT_MATCH("2009", "Existing password is incorrect. Please try again.", HttpStatus.BAD_REQUEST),
    USER_LOGIN_UPDATE_ERROR("2010", "Error occurred when updating the user login", HttpStatus.BAD_REQUEST),
    DATA_NOT_EXIST("2011", "User data doesn't exist for given Id.", HttpStatus.BAD_REQUEST),
    USER_PROFILE_GET_ERROR("2012", "Error occurred when retrieving user information.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_LOGGED_IN("2013", "User already logged in. Please log out and try again", HttpStatus.BAD_REQUEST),
    USER_NOT_LOGGED_IN("2015", "User not logged in. Log in and try again.", HttpStatus.BAD_REQUEST),
    USER_LOGOUT_ERROR("2016", "Error occurred when logging the user out", HttpStatus.BAD_REQUEST),
    INVALID_TIME("2017", "The total hours for work, social, entertainment, and sleep must equal 24.", HttpStatus.BAD_REQUEST),
    DATA_LOG_ERROR("2018", "Error occurred while creating the data log", HttpStatus.BAD_REQUEST),
    PREDICTION_ERROR("2019", "You can only have your mood predicted once per day. Please try again tomorrow.", HttpStatus.BAD_REQUEST),
    INVALID_DATA("2020", "The data entered is invalid.", HttpStatus.BAD_REQUEST),
    CONNECTION_ERROR("2021", "Error occurred while connecting to ML model", HttpStatus.BAD_REQUEST),
            ;
    ;

    private final String code;
    private final String desc;
    private final HttpStatus httpStatus;

    DomainErrorCode(String code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.desc = description;
        this.httpStatus = httpStatus;
    }
}
