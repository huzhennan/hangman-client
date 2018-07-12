package com.example.hangman.entity;

import com.example.hangman.entity.WordInfo;
import lombok.Data;

/**
 * Created by zhennan.hu on 18-7-9
 */
@Data
public class MyInfo {
    private String email;
    private String sessionId;
    private int numberOfGuessAllowedForEachWord;
    private int numberOfWordsToGuess;

//    private WordInfo currentWordInfo;

    public MyInfo(String email, String sessionId) {
        this.email = email;
        this.sessionId = sessionId;
    }
}
