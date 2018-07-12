package com.example.hangman.entity;

import com.example.hangman.json.NextWordResponse;
import lombok.Data;

import java.util.*;

/**
 * Created by zhennan.hu on 18-7-9
 */
@Data
public class WordInfo {
    private Integer wrongGuessCountOfCurrentWord;
    private Integer totalWordCount;
    private String word;

    private int guessId = 0;
    private Set<String> wrongGuessLetters;
    private Set<String> rightGuessLetters;

    public WordInfo(Integer wrongGuessCountOfCurrentWord, Integer totalWordCount, String word) {
        this.wrongGuessCountOfCurrentWord = wrongGuessCountOfCurrentWord;
        this.totalWordCount = totalWordCount;
        this.word = word;

        wrongGuessLetters = new HashSet<>();
        rightGuessLetters = new HashSet<>();
    }

    public void update(NextWordResponse response, String guessLetter) {
        this.guessId += 1;
        // 如果返回的结果word与当前的word相等,认为猜错了
        String resultWord = response.getData().getWord();
        if (word.equals(resultWord)) {
            wrongGuessLetters.add(guessLetter);
        } else {
            rightGuessLetters.add(guessLetter);
        }

        word = response.getData().getWord();
        wrongGuessCountOfCurrentWord = response.getData().getWrongGuessCountOfCurrentWord();
        totalWordCount = response.getData().getTotalWordCount();
    }

    public int getWordLength() {
        return Optional.ofNullable(this.word).map(String::length).orElse(0);
    }
}
