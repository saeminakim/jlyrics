package com.project.jlyrics.song.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SongServiceTest {

    /* 사용자는 노래를 검색할 수 있어야 한다 (제목/가수/가사 일부 등)
    *  사용자가 특정 키워드를 입력 후 검색 버튼을 클릭했을 때 결과가 나온다
    * 사용자가 검색 결과 리스트에서 일본노래만 볼 수 있다
    * */

    @Test
    @DisplayName("사용자가 특정 키워드를 입력했을 때 결과가 나온다") // 입력과 출력
    void songSearch() {
        // 유키
        String keyword = "유키";
        ArrayList<Map<String, String>> result = searchSongsByKeyword(keyword);

        if (result.size() <= 0) {
            Assertions.fail();
        }

        Map<String, String> resultMap = result.get(0);
        String title = resultMap.get("title");

        if (ObjectUtils.isEmpty(title)) {
            Assertions.fail();
        }

        if (!"유키노하나".equals(title)) {
            Assertions.fail();
        }
    }

    ArrayList<Map<String, String>> searchSongsByKeyword (String keyword) { // 내가 만든 함수는 입력 받지만 테스트 함수는 입력 안 받음
        ArrayList<Map<String, String>> rtnList = new ArrayList<>();
        Map<String, String> song = new HashMap<>();
        song.put("title", "유키노하나");
        rtnList.add(song);
        return rtnList;
    }

    @Test
    @DisplayName("키워드를 입력했을 때 제한보다 길면 길이 제한만큼 잘린 검색어가 나온다")
    void checkKeywordLengthForLongKeyword () {
        // 1. 길게 입력했을 때는 잘린 키워드 나온다 2. 짧게 입력하면 그대로 나온다 두개의 테스트 필요
        String keyword = "qwerasdfzxcvtyuiopfghjqwerasdfzxcvtyuiopfghjqwerasdfzxcvtyuiopfghj";

        String shortenKeyword = checkKeywordLengthAndReturnProperKeyword(keyword);

        if (shortenKeyword.length() > 30) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("키워드를 입력했을 때 제한보다 짧으면 그대로 나온다")
    void checkKeywordLengthForShortKeyword () {
        // 1. 길게 입력했을 때는 잘린 키워드 나온다 2. 짧게 입력하면 그대로 나온다 두개의 테스트 필요
        String keyword = "qwerasdfzx";

        String shortenKeyword = checkKeywordLengthAndReturnProperKeyword(keyword);

        if (shortenKeyword.length() > 30) {
            Assertions.fail();
        }
    }

    String checkKeywordLengthAndReturnProperKeyword(String keyword) {
        String rtnKeyword = "";

        if (keyword.length() > 30) {
            rtnKeyword = keyword.substring(0, 30);
        } else {
            rtnKeyword = keyword;
        }

        return rtnKeyword;
    }
}
