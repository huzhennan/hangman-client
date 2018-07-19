package com.example.hangman;

import com.example.hangman.service.PlayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HangmanClientApplication {
    @Value("${hangman.test.email}")
    private String email;

    public static void main(String[] args) {
        SpringApplication.run(HangmanClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner play(PlayService playService) {
        return args -> {
            playService.play();
        };
    }
}
