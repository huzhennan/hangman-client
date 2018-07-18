package com.example.hangman;

import com.example.hangman.entity.MyInfo;
import com.example.hangman.entity.WordInfo;
import com.example.hangman.json.GameOnResponse;
import com.example.hangman.json.NextWordResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class HangmanClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(HangmanClientApplication.class, args);
    }

    //	@Bean
    public CommandLineRunner demo(MyInfo myInfo) {
        return args -> {
            String email = "bb@aa.com";
            startGame(myInfo, email);
            String sessionId = myInfo.getSessionId();

            int numberOfWordsToGuess = myInfo.getNumberOfWordsToGuess();
            for (int i = 0; i < numberOfWordsToGuess; i++) {
                NextWordResponse response = Requests.nextWord(sessionId);
                WordInfo currentWordInfo = new WordInfo(
                        response.getData().getWrongGuessCountOfCurrentWord(),
                        response.getData().getTotalWordCount(),
                        response.getData().getWord()
                );

                guessOneWord(currentWordInfo);
            }


        };
    }

    private void guessOneWord(WordInfo wordInfo) {

    }

    private void startGame(MyInfo myInfo, String email) throws IOException {
        myInfo.setEmail(email);

        GameOnResponse response = Requests.start(email);
        myInfo.setSessionId(response.getSessionId());
        myInfo.setNumberOfWordsToGuess(response.getData().getNumberOfWordsToGuess());
        myInfo.setNumberOfGuessAllowedForEachWord(response.getData().getNumberOfGuessAllowedForEachWord());
    }
}
