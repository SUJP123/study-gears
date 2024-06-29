package com.collegeproject.studygears.service;

import com.collegeproject.studygears.service.YouTubeService;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class YoutubeServiceTest implements CommandLineRunner {

    private final YouTubeService youTubeService;

    @Autowired
    public YoutubeServiceTest(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @Override
    public void run(String... args) throws Exception {
        String query = "Java programming tutorial";
        List<SearchResult> videos = youTubeService.searchVideos(query);
        for (SearchResult video : videos) {
            System.out.println("Video: " + video.getSnippet().getTitle());
        }
    }
}
