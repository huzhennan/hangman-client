package com.example.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by zhennan.hu on 18-7-10
 */
public class Test {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("[e]");

        Map<Object, Long> result = "helloiiii".chars().boxed().collect(Collectors.groupingBy(i -> i, Collectors.counting()));
//        System.out.println("hello".toCharArray());
        System.out.println(result);
    }
}
