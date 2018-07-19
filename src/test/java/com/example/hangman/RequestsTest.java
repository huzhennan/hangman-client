package com.example.hangman;

import com.example.hangman.json.GameOnResponse;
import com.example.hangman.json.NextWordResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * Created by zhennan.hu on 18-7-9
 */
public class RequestsTest extends BaseTest {
    @Autowired
    private Requests requests;
    @Value("${hangman.test.email}")
    private String testEmail;

    @Test
    public void testStartGame() throws IOException {
        System.out.println("---------------------------");
        GameOnResponse response = requests.start(testEmail);
        System.out.println("response: " + response);
        System.out.println("---------------------------");
    }

    @Test
    public void testNextWord() throws IOException {
        System.out.println("-----------------");
        GameOnResponse response = requests.start(testEmail);
        NextWordResponse nextWordResponse = requests.nextWord(response.getSessionId());
        System.out.println("resp: " + nextWordResponse);
        System.out.println("-----------------");
    }

    @Test
    public void testGuess() throws IOException {
        System.out.println("-----------------");
        GameOnResponse response = requests.start(testEmail);
        NextWordResponse nextWordResponse = requests.nextWord(response.getSessionId());
        NextWordResponse guessResponse = requests.guess(response.getSessionId(), "A");
        System.out.println("response: " + guessResponse);
        System.out.println("-----------------");
    }
}
