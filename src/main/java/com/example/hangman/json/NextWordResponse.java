package com.example.hangman.json;

import lombok.Data;

/**
 * Created by zhennan.hu on 18-7-9
 */
@Data
public class NextWordResponse {
    private String sessionId;
    private DataWrapper data;

    @Data
    public static class DataWrapper {
        private Integer wrongGuessCountOfCurrentWord;
        private Integer totalWordCount;
        private String word;
    }
}
