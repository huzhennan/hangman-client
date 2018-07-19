package com.example.hangman.service;

import com.example.hangman.BaseTest;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by zhennan.hu on 18-7-10
 */
public class WordsServiceTest extends BaseTest {
    @Autowired
    private WordsService wordsService;

    @Test
    public void testNearestLetter() {
        Set<String> wrongLetters = Sets.newHashSet("E", "F", "S", "Z", "X", "W", "A");
        Set<String> rightLetters = Sets.newHashSet( "V", "K");

        // client
        String result = wordsService.nearestLetter("*****", wrongLetters, rightLetters);
        System.out.println("----------------------");
        System.out.println("result: " + result);
        System.out.println("------------------");
    }

    @Test
    public void testNearestLetterWithEmpty() {
        Set<String> wrongLetters = Sets.newHashSet();
        Set<String> rightLetters = Sets.newHashSet("I", "E", "N", "T");

        String result = wordsService.nearestLetter("**IENT", wrongLetters, rightLetters);
        System.out.println("----------------------");
        System.out.println("result: " + result);
        System.out.println("------------------");
    }

    @Test
    public void testFoo() {
        String aa = "\\w\\wllo";

        String foo = String.format("^%s$", "**IENT".replaceAll("\\*", "\\\\w"));
        Pattern p = Pattern.compile(foo);
        System.out.println(foo);
        System.out.println(p.matcher("CLIENT").find());
    }

    @Test
    public void testGetNearestWordList() {
        System.out.println("-------------------------");
        Set<String> wrongLetters = Sets.newHashSet();
        Set<String> rightLetters = Sets.newHashSet("S", "H");
        List<String> result = wordsService.getNearestWordList("SH**", wrongLetters, rightLetters);
        result.forEach(System.out::println);
        String letter = wordsService.nearestLetter("*O***SH**", wrongLetters, rightLetters);
        System.out.println("letter: " + letter);
        System.out.println("-------------------------");
    }

    @Test
    public void testSplitWord() {
        System.out.println("--------------------------");
        List<String> wordList = Arrays.asList("hello", "world");
        Map<String, Long> result = wordList.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(letter -> letter, Collectors.counting()));

        System.out.println("result: " + result);
        System.out.println("--------------------------");
    }
}
