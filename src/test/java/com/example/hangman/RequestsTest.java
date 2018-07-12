package com.example.hangman;

import com.example.hangman.json.GameOnResponse;
import com.example.hangman.json.NextWordResponse;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zhennan.hu on 18-7-9
 */
public class RequestsTest extends BaseTest {
    @Test
    public void testStartGame() throws IOException {
        System.out.println("---------------------------");
        GameOnResponse response = Requests.start("aa@bb.com");
        System.out.println("response: " + response);
        System.out.println("---------------------------");
    }

    @Test
    public void testNextWord() throws IOException {
        System.out.println("-----------------");
        GameOnResponse response = Requests.start("aa@bb.com");
        NextWordResponse nextWordResponse = Requests.nextWord(response.getSessionId());
        System.out.println("resp: " + nextWordResponse);
        System.out.println("-----------------");
    }

    @Test
    public void testGuess() throws IOException {
        System.out.println("-----------------");
        GameOnResponse response = Requests.start("aa@bb.com");
        NextWordResponse nextWordResponse = Requests.nextWord(response.getSessionId());
        NextWordResponse guessResponse = Requests.guess(response.getSessionId(), "A");
        System.out.println("response: " + guessResponse);
        System.out.println("-----------------");
    }
}
