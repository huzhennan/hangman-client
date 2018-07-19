package com.example.hangman.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Created by zhennan.hu on 18-7-9
 */
@Service
public class WordsService {
    public static final Logger log = LoggerFactory.getLogger(WordsService.class);
    private List<String> words;

    public WordsService() {
        log.debug("---------------------------------");
        log.debug("load english words from words.txt");
        log.debug("---------------------------------");
        try {
            Path path = Paths.get(getClass().getClassLoader().getResource("words.txt").toURI());
            words = Files.readAllLines(path).stream()
                    .filter(s -> s.matches("^[a-zA-Z]+$"))
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getWords() {
        return words;
    }

    public List<String> getNearestWordList(String fuzzyWord, Set<String> wrongGuessLetters,
                                           Set<String> rightGuessLetters) {
        Pattern p = Pattern.compile(String.format("^%s$", fuzzyWord.replaceAll("\\*", "\\\\w")));

        return words.stream()
                .filter(s -> p.matcher(s).find())
                .filter(s -> wrongGuessLetters.stream().noneMatch(s::contains))
                .filter(s -> rightGuessLetters.stream().allMatch(s::contains))
                .collect(Collectors.toList());
    }

    public String nearestLetter(String fuzzyWord, Set<String> wrongGuessLetters, Set<String> rightGuessLetters) {
        List<String> nearestWords = getNearestWordList(fuzzyWord, wrongGuessLetters, rightGuessLetters);
        Map<String, Long> letterStats = nearestWords.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(letter -> letter, Collectors.counting()));

        String result = letterStats.entrySet().stream()
                .filter(keyCountEntry -> !rightGuessLetters.contains(keyCountEntry.getKey())
                        && !wrongGuessLetters.contains(keyCountEntry.getKey()))
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RuntimeException("no letter found."));

        return result;
    }
}
