package com.example.hangman.service;

import com.example.hangman.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by zhennan.hu on 18-7-11
 */
public class PlayServiceTest extends BaseTest {
    @Autowired
    private PlayService playService;

    @Test
    public void testStartGame() throws IOException {
        System.out.println("------------------------");
        playService.startGame("bb@163.com");
        System.out.println(playService.getMyInfo());
        System.out.println("------------------------");
    }

    @Test
    public void testPlay() throws IOException {
        System.out.println("-------------------");
        playService.play();
        System.out.println("my info: " + playService.getMyInfo());
        System.out.println(" 111: " + playService.getCurrentWordInfo());
        System.out.println("-------------------");
    }
}
