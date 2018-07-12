package com.example.hangman.json;

import lombok.Data;

/**
 * Created by zhennan.hu on 18-7-12
 */
@Data
public class ResultResponse {
    private String sessionId;
    private DataWrapper data;

    @Data
    public static class DataWrapper {
        private Integer score;
        private Integer wrongGuessCountOfCurrentWord;
        private Integer totalWordCount;
        private String word;
    }
}
