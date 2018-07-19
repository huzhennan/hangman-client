package com.example.hangman.service;

import com.example.hangman.Requests;
import com.example.hangman.entity.MyInfo;
import com.example.hangman.entity.WordInfo;
import com.example.hangman.json.GameOnResponse;
import com.example.hangman.json.NextWordResponse;
import com.example.hangman.json.ResultResponse;
import com.example.hangman.json.SubmitResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * Created by zhennan.hu on 18-7-11
 */
@Service
public class PlayService {
    public static final Logger log = LoggerFactory.getLogger(PlayService.class);
    private MyInfo myInfo;
    private WordInfo currentWordInfo;
    private Queue<String> actions = new LinkedList<>();
    private int score = 0;
    private String myEmail;

    @Autowired
    private WordsService wordsService;
    @Autowired
    private Requests requests;

    public void startGame(String email) throws IOException {
        GameOnResponse response = requests.start(email);
        myInfo = new MyInfo(email, response.getSessionId());
        myInfo.setNumberOfGuessAllowedForEachWord(response.getData().getNumberOfGuessAllowedForEachWord());
        myInfo.setNumberOfWordsToGuess(response.getData().getNumberOfWordsToGuess());
    }

    public void nextWord() throws IOException {
        String sessionId = myInfo.getSessionId();
        NextWordResponse response = requests.nextWord(sessionId);

        currentWordInfo = new WordInfo(response.getData().getWrongGuessCountOfCurrentWord(),
                response.getData().getTotalWordCount(),
                response.getData().getWord());
    }

    public void guess() throws IOException {
        String sessionId = myInfo.getSessionId();
        String letter = wordsService.nearestLetter(currentWordInfo.getWord(),
                currentWordInfo.getWrongGuessLetters(), currentWordInfo.getRightGuessLetters());
        log.debug("guess {} >>>> ", letter);
        NextWordResponse response = requests.guess(sessionId, letter);
        log.debug("guess result <<<< {}", response);
        if (!response.getData().getWord().contains("*")) {
            int currentWordScore = 20 - response.getData().getWrongGuessCountOfCurrentWord();
            log.debug("current word score <<< {}", currentWordScore);
            score += currentWordScore;
            log.debug("total score <<< {}", score);
        }

        currentWordInfo.update(response, letter);
    }

    public String nextAction() {
        if (actions.size() > 0) {
            return actions.poll();
        }

        if (myInfo == null) {
            actions.offer("start");
        } else if (currentWordInfo == null ||
                (needNextWord()) && currentWordInfo.getTotalWordCount() < myInfo.getNumberOfWordsToGuess()) {
            actions.offer("next");
        } else if (needNextWord() && currentWordInfo.getTotalWordCount() == myInfo.getNumberOfWordsToGuess()) {
            actions.offer("result");
        } else {
            actions.offer("guess");
        }

        return actions.poll();
    }

    private boolean needNextWord() {
        return (!currentWordInfo.getWord().contains("*") || currentWordInfo.getGuessId() == 10);
    }

    public void play(String email) throws IOException {
        myEmail = email;

        for (int i = 0; i < 10000; i++) {
            String action = nextAction();
            handleAction(action);

            if (action.equals("result")) {
                break;
            }
        }
    }

    private void handleAction(String action) throws IOException {
        log.debug("action {} >>>> ", action);
        switch (action) {
            case "start":
                startGame(myEmail);
                break;
            case "next":
                nextWord();
                break;
            case "guess":
                guess();
                break;
            case "result":
                getResult();
                break;
        }
    }

    private void getResult() throws IOException {
        String sessionId = myInfo.getSessionId();
        ResultResponse response = requests.getResult(sessionId);

        Integer score = response.getData().getScore();
        log.debug("++++++++++++++++ result +++++++++++++++++++++");
        log.debug("score: {}", response.getData().getScore());
        log.debug("++++++++++++++++ end    +++++++++++++++++++++");

        // 如果score大于1000, 上传
        Optional.ofNullable(score)
                .filter(s -> s > 1000)
                .ifPresent(s -> {
                    try {
                        submitResult();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void submitResult() throws IOException {
        String sessionId = myInfo.getSessionId();
        SubmitResultResponse submitRlt = requests.submitResult(sessionId);

        log.debug("-------------- submit -----------------------");
        log.debug("submit result: {}", submitRlt);
        log.debug("-------------- end -----------------------");

    }

    public MyInfo getMyInfo() {
        return myInfo;
    }

    public WordInfo getCurrentWordInfo() {
        return currentWordInfo;
    }
}
