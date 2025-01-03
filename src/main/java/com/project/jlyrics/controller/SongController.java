package com.project.jlyrics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.jlyrics.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class SongController {

    private final SongService songService;
    @Autowired
    private RestTemplate restTemplate;

    private final String GENIUS_API_URL = "https://api.genius.com/search";

    @Value("${genius.api.token}")
    private String ACCESS_TOKEN;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/search")
    public String createSearch() {
        return "search";
    }


    @PostMapping( "/search")
    public String searchSong(Model model, @RequestParam("searchKeyword") String searchKeyword) {
        // 헤더에 Access Token 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ACCESS_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Genius API 호출
        String url = GENIUS_API_URL + "?q=" + searchKeyword;
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Object responseArr = response.getBody().get("response");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(responseArr, Map.class);
        ArrayList<Map<String, Object>> resultArr = objectMapper.convertValue(map.get("hits"), ArrayList.class);

        ArrayList<Map<String, Object>> rtnMap = new ArrayList<>();
        for (Map<String, Object> result : resultArr) {
            rtnMap.add((Map<String, Object>) result.get("result"));
        }

        model.addAttribute("songs", rtnMap);

        return "searchResult";
    }
}
