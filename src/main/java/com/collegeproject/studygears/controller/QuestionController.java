package com.collegeproject.studygears.controller;

import com.collegeproject.studygears.service.OpenAiService;
import com.collegeproject.studygears.service.YouTubeService;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final OpenAiService openAiService;
    private final YouTubeService youtubeService;

    @Autowired
    public QuestionController(OpenAiService openAiService, YouTubeService youtubeService) {
        this.openAiService = openAiService;
        this.youtubeService = youtubeService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> askQuestion(@RequestBody Map<String, String> questionPayload) {
        String question = questionPayload.get("question");

        // Get answer from OpenAI
        String answer = openAiService.getAnswer(question);

        // Get related videos from YouTube
        List<SearchResult> videoResults;
        try {
            videoResults = youtubeService.searchVideos(question);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Map video results to a simpler structure
        List<Map<String, String>> videos = videoResults.stream().map(result -> Map.of(
                "title", result.getSnippet().getTitle(),
                "description", result.getSnippet().getDescription(),
                "url", "https://www.youtube.com/watch?v=" + result.getId().getVideoId()
        )).toList();

        // Combine answer and videos in the response
        Map<String, Object> response = Map.of(
                "answer", answer,
                "videos", videos
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
