package com.team555.inu.ringmybell_mainserver.server.networking.android;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// 안드로이드를 위한 웹소켓 서버 Thread Class
@AllArgsConstructor
@Slf4j
@Component
public class ServerForAndroid {

    // 안드로이드를 위해 개방할 포트 번호
    private final int android_port = 5555;

    // ListentingToAndroid Thread 참조변수
    private ListeningToAndroid listeningToAndroid;

    @Async
    public void run(){
        log.info("안드로이드를 위한 웹소켓 서버 시작!!");

        try {
            // 위에서 설정한 포트 번호로 서버소켓 생성
            ServerSocket serverSocketForAndroid = new ServerSocket(android_port);
            log.info("안드로이드를 위한 서버 소켓 생성 완료");

            // 소켓 클라이언트 접속 대기 무한루프
            while(true){
                log.info("안드로이드 클라이언트 소켓 접속 대기");

                // 안드로이드 클라이언트 소켓이 서버소켓에 연결할 때 까지 무한대기
                Socket connectedSocketWithAndroid = serverSocketForAndroid.accept();
                log.info("새로 접속한 안드로이드 클라이언트 소켓 : " + connectedSocketWithAndroid);

                // ListeningToAndroid Thread 시작
                listeningToAndroid.run(connectedSocketWithAndroid);

            }
        } catch (IOException e) {
            log.error("ServerForAndroid Thread의 run()함수에서 에러 발생");
            e.printStackTrace();
        }
    }
}
