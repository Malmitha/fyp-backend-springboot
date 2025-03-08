package backend.MoodBuddy.external;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Component
public class MLModelClient {

    private final String ML_MODEL_URL = "http://127.0.0.1:5000/predict"; // Flask API endpoint

    public int getPrediction(String jsonPayload) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare headers and request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        // Send POST request to the ML model
        ResponseEntity<String> response = restTemplate.postForEntity(ML_MODEL_URL, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Parse response to get prediction
            JSONObject jsonResponse = new JSONObject(response.getBody());
            return jsonResponse.getInt("prediction");
        } else {
            throw new Exception("Failed to get prediction. HTTP Status: " + response.getStatusCode());
        }
    }
}


