package com.team555.inu.ringmybell_mainserver.server.networking.android;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

@Slf4j
@Component
public class SendOnceToAndroid {

    @Async
    public void run(Socket connectedSocketWithAndroid, String JSONDataStr){
        try{
            // 안드로이드에 클라이언트에 연결된 소켓 객체가 닫혔다면
            // SendingToAndroid 쓰레드 종료
            if(connectedSocketWithAndroid.isClosed()){
                log.info("SendingToAndroid Thread 종료");
                return;
            }
            // 안드로이드에 연결된 소켓 클라이언트에 대해
            // 버퍼Writer 소켓 스트림 생성 및 할당
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connectedSocketWithAndroid.getOutputStream(), "UTF-8"));

            log.info("안드로이드 클라이언트로 JSON String data 전송");

            // 데이터 확인
            log.info("안드로이드에 전송하는 데이터 : " + JSONDataStr);

            // JSON형태의 문자열 데이터를
            // 버퍼 Writer을 통해 안드로이드 클라이언트로 보낸다.
            bufferedWriter.write(JSONDataStr);

            // 수신측 BufferdReader의 readLine()함수를 통해 문자열 데이터를 수신하려면
            // 송신측의 inputStream에는  반드시 개행문자가 포함되어야 한다.
            // 자바에서의 개행문자는 "\n" 이지만,
            // 스트림에서의 개행문자는 "\r\n"이 개행문자이다.
            // 따라서, 보내는쪽 스트림의 의 데이터 뒤에 "\r\n"을 반드시 붙여야한다.
            // BufferedWriter에서 이 역할을
            // newLine()함수가 수행한다.
            bufferedWriter.newLine();
            //남아있는 데이터를 모두 출력시킴
            bufferedWriter.flush();

        }catch(Exception e){
            log.error("SendOnceToAndroid Thread의 run()에서 에러발생");
            e.printStackTrace();
        }
    }
}
