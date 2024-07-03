package com.collegeproject.studygears.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YouTubeService {

    private static final String APPLICATION_NAME = "StudyGears";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final long NUMBER_OF_VIDEOS_RETURNED = 5;

    private final YouTube youtube;

    @Value("YOUTUBE_API_KEY_VALUE")
    private String youtubeApiKey;

    public YouTubeService() throws Exception {
        youtube = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, request -> {})
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<SearchResult> searchVideos(String query) throws Exception {
        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(youtubeApiKey);
        search.setQ(query);
        search.setType("video");
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

        SearchListResponse searchResponse = search.execute();
        return searchResponse.getItems();
    }
}
