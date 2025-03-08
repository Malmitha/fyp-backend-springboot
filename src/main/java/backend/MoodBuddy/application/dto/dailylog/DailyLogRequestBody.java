package backend.MoodBuddy.application.dto.dailylog;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This is the class for create daily log request body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyLogRequestBody {

    @NotNull(message = "User id must not be null")
    private Long userId;

    @NotNull(message = "Work hours must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Work hours must be at least 0")
    @DecimalMax(value = "24.0", inclusive = true, message = "Work hours must not exceed 24")
    private double workHours;

    @NotNull(message = "Social hours must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Social hours must be at least 0")
    @DecimalMax(value = "24.0", inclusive = true, message = "Social hours must not exceed 24")
    private double socialHours;

    @NotNull(message = "Entertainment hours must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Entertainment hours must be at least 0")
    @DecimalMax(value = "24.0", inclusive = true, message = "Entertainment hours must not exceed 24")
    private double entertainmentHours;

    @NotNull(message = "Sleep hours must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Sleep hours must be at least 0")
    @DecimalMax(value = "24.0", inclusive = true, message = "Sleep hours must not exceed 24")
    private double sleepHours;

    @NotNull(message = "Stress Level must not be null")
    @Min(value = 1, message = "Stress level must be at least 1")
    @Max(value = 10, message = "Stress level must not exceed 10")
    private int stressLevel;

    @NotNull(message = "Physical Activity Level must not be null")
    @Min(value = 1, message = "Physical activity level must be at least 1")
    @Max(value = 10, message = "Physical activity level must not exceed 10")
    private int physicalActivityLevel;

    @NotNull(message = "Sleep Quality must not be null")
    @Min(value = 1, message = "Sleep quality must be at least 1")
    @Max(value = 10, message = "Sleep quality must not exceed 10")
    private int sleepQuality;

    @NotNull(message = "Well Being Score must not be null")
    @Min(value = 1, message = "Well-being score must be at least 1")
    @Max(value = 10, message = "Well-being score must not exceed 10")
    private int wellBeingScore;

}
