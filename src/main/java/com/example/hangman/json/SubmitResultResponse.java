package com.example.hangman.json;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhennan.hu on 18-7-19
 */
@Data
public class SubmitResultResponse {
    private String message;
    private String sessionId;
    private DataWrapper data;

    @Data
    public static class DataWrapper {
        private String playerId;
        private String sessionId;
        private Integer totalWordCount;
        private Integer correctWordCount;
        private Integer totalWrongGuessCount;
        private Integer score;
        private Date datetime;
    }
}
