package com.collegeproject.studygears.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAiService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiService.class);

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final RestTemplate restTemplate;

    public OpenAiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAnswer(String question) {
        logger.info("Received question: {}", question);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", question);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Map[] { message });
        requestBody.put("max_tokens", 150);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(openAiApiUrl, HttpMethod.POST, entity, JsonNode.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode responseBody = response.getBody();
                if (responseBody != null) {
                    return responseBody.get("choices").get(0).get("message").get("content").asText();
                }
            }
        } catch (HttpClientErrorException.TooManyRequests e) {
            logger.error("Rate limit exceeded: {}", e.getMessage());
            return "Rate limit exceeded. Please try again later.";
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error: {}", e.getMessage());
            return "An error occurred while processing your request.";
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            return "An unexpected error occurred.";
        }
        return "Sorry, I couldn't process your request.";
    }
}
