package com.collegeproject.studygears.controller;

import com.collegeproject.studygears.service.OpenAiService;
import com.collegeproject.studygears.service.YouTubeService;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/studybot")
@CrossOrigin(origins = {"https://study-gears-11k9qh3pf-sujp123s-projects.vercel.app"})
public class QuestionController {

    private final OpenAiService openAiService;
    private final YouTubeService youTubeService;

    @Autowired
    public QuestionController(OpenAiService openAiService, YouTubeService youTubeService) {
        this.openAiService = openAiService;
        this.youTubeService = youTubeService;
    }

    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(@RequestBody Map<String, String> payload) {
        try {
            String question = payload.get("question");
            String answer = openAiService.getAnswer(question);

            List<SearchResult> videos = youTubeService.searchVideos(question);

            Map<String, Object> response = new HashMap<>();
            response.put("answer", answer);
            response.put("videos", videos);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing your request.");
        }
    }
}
