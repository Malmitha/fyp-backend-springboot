package backend.MoodBuddy.domain.service;

import backend.MoodBuddy.application.dto.dailylog.DailyLogRequestBody;
import backend.MoodBuddy.external.MLModelClient;

import backend.MoodBuddy.external.exceptions.DomainErrorCode;
import backend.MoodBuddy.external.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {

    @Autowired
    private MLModelClient mlModelClient;

    public int predictMood(DailyLogRequestBody body, String occupation, int age, String gender) throws DomainException {
        try {
            int genderType = setGender(gender);
            int occupationType = setOccupation(occupation);

            JSONObject payload = new JSONObject();
            payload.put("gender", genderType);
            payload.put("age", age);
            payload.put("sleepQuality", body.getSleepQuality());
            payload.put("physicalActivityLevel", body.getPhysicalActivityLevel());
            payload.put("stressLevel", body.getStressLevel());
            payload.put("socialHours", body.getSocialHours());
            payload.put("entertainmentHours", body.getEntertainmentHours());
            payload.put("workHours", body.getWorkHours());
            payload.put("sleepHours", body.getSleepHours());
            payload.put("wellBeingScore", body.getWellBeingScore());
            payload.put("occupation", occupationType);

            // Send payload to ML model and get prediction
            return mlModelClient.getPrediction(payload.toString());
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.CONNECTION_ERROR.getCode(), DomainErrorCode.CONNECTION_ERROR.getDesc());
        }
    }

    private int setGender(String gender) throws DomainException {
        int value = switch (gender) {
            case "male" -> 0;
            case "female" -> 1;
            default ->
                    throw new DomainException(DomainErrorCode.INVALID_DATA.getCode(), DomainErrorCode.INVALID_DATA.getDesc());
        };
        return value;
    }

    private int setOccupation (String occupation) throws DomainException {
        int value = switch (occupation) {
            case "Accountant" -> 0;
            case "Doctor" -> 1;
            case "Engineer" -> 2;
            case "Lawyer" -> 3;
            case "Manager" -> 4;
            case "Nurse" -> 5;
            case "Sales Representative" -> 6;
            case "Salesperson" -> 7;
            case "Scientists" -> 8;
            case "Software Engineer" -> 9;
            case "Student" -> 10;
            case "Teacher" -> 11;
            default ->
                    throw new DomainException(DomainErrorCode.INVALID_DATA.getCode(), DomainErrorCode.INVALID_DATA.getDesc());
        };
        return value;
    }
}
