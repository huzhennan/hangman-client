package com.example.hangman.json;

import lombok.Data;

/**
 * Created by zhennan.hu on 18-7-9
 */
@Data
public class GameOnResponse {
    private String message;
    private String sessionId;
    private DateWrapper data;

    @Data
    public static class DateWrapper {
        private int numberOfGuessAllowedForEachWord;
        private int numberOfWordsToGuess;
    }
}
