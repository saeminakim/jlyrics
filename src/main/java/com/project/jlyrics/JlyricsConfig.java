package com.project.jlyrics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JlyricsConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
