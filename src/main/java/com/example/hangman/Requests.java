package com.example.hangman;

import com.example.hangman.json.GameOnResponse;
import com.example.hangman.json.NextWordResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by zhennan.hu on 18-7-9
 */
public class Requests {
    public static final Logger log = LoggerFactory.getLogger(Requests.class);
//    private static final String BASE_URL = "http://172.105.234.63:8080";
private static final String BASE_URL = "http://localhost:8080";
    private static final String URL = BASE_URL + "/game/on";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    //language=JSON
    private static final String START_GAME_JSON_FORMAT = "{\n" +
            "  \"action\": \"startGame\",\n" +
            "  \"playerId\": \"%s\"\n" +
            "}";

    private static final String NEXT_WORD_JSON_FORMAT = "{\n" +
            "  \"sessionId\": \"%s\",\n" +
            "  \"action\": \"nextWord\"\n" +
            "}";

    private static final String GUESS_JSON_FORMAT = "{\n" +
            "  \"sessionId\": \"%s\",\n" +
            "  \"action\": \"guess\",\n" +
            "  \"guess\": \"%s\"\n" +
            "}";


    public static GameOnResponse start(String email) throws IOException {
        String json = String.format(START_GAME_JSON_FORMAT, email);
        return request(json, GameOnResponse.class);
    }

    public static NextWordResponse nextWord(String sessionId) throws IOException {
        String json = String.format(NEXT_WORD_JSON_FORMAT, sessionId);
        return request(json, NextWordResponse.class);
    }

    public static NextWordResponse guess(String sessionId, String letter) throws IOException {
        String json = String.format(GUESS_JSON_FORMAT, sessionId, letter);
        return request(json, NextWordResponse.class);
    }

    public static <T> T request(String json, Class<T> clazz) throws IOException {
        HttpRequest request = HttpRequest.post(URL)
                .contentType("application/json")
                .send(json);
        if (request.code() == 200) {
            return objectMapper.readValue(request.body(), clazz);
        }

        log.error("request error: " + request.code());
        log.error("response start: ");
        log.error(request.body());
        log.error("response end");

        throw new RuntimeException("request Failed");
    }
}
