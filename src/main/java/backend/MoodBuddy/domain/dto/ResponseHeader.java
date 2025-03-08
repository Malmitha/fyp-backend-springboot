package backend.MoodBuddy.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * This is the class for the response header
 */
@Getter
@Setter
@Data
public class ResponseHeader {
    private String requestId;
    private String timestamp;
    private String code;
    private String desc;

    @JsonIgnore
    private String responseCode;

    public ResponseHeader(String requestId, String timestamp, String code, String desc) {
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.code = code;
        this.desc = desc;
    }

    public ResponseHeader(String requestId, String timestamp, String code, String desc, String responseCode) {
        this(requestId, timestamp, code, desc);  // Reuse the first constructor
        this.responseCode = responseCode;
    }
}
