package com.team555.inu.ringmybell_mainserver.server.networking.android;

import com.team555.inu.ringmybell_mainserver.server.control.DataAnalyzeController;
import com.team555.inu.ringmybell_mainserver.server.sockets.AndroidSockets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@AllArgsConstructor
@Slf4j
@Component
public class ListeningToAndroid {

    private final DataAnalyzeController dataAnalyzeController;
    private final AndroidSockets androidSockets;

    @Async
    public void run(Socket connectedSocketWithAndroid){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connectedSocketWithAndroid.getInputStream(), "UTF-8"));
            log.info("안드로이드 클라이언트에 연결된 소켓으로 BufferedReader 생성");

            log.info("안드로이드 클라이언트로부터 데이터 수신 시작");
            // 무한루프 돌면서 안드로이드로부터 데이터 받음
            while(true){
                log.info("안드로이드 클라이언트로부터 데이터 수신 대기중");

                // 수신측 BufferdReader의 readLine()함수를 통해 문자열 데이터를 수신하려면
                // 송신측의 inputStream에는  반드시 개행문자가 포함되어야 한다.
                // 자바에서의 개행문자는 "\n" 이지만,
                // 스트림에서의 개행문자는 "\r\n"이 개행문자이다.
                // 따라서, 보내는쪽 스트림의 의 데이터 뒤에 "\r\n"을 반드시 붙여야한다.
                // BufferedWriter에서 이 역할을
                // newLine()함수가 수행한다.
                // readLine()은 상대 BufferdWriter가 끝에 "\r\n"를 붙인 데이터를 전송할 때 까지 대기
                String JSONDataStr = bufferedReader.readLine();

                // 안드로이드 클라이언트 측에서 소켓을 닫으면,
                // JSONDataStr 값이 null이 됨
                if(JSONDataStr == null){
                    log.info("ListeningToAndroid : 안드로이드 클라이언트 측에서 소켓 닫음");

                    // 중앙 ArrayList에서 연결이 끊긴 socket에 대한 StoreAndroid객체 삭제
                    androidSockets.deleteStoredAndroid(connectedSocketWithAndroid);

                    // 안드로이드에 클라이언트에 연결된 소켓 객체 닫음
                    connectedSocketWithAndroid.close();

                    break;
                }
                log.info("안드로이드 클라이언트로부터 받은 데이터 : "+JSONDataStr);

                dataAnalyzeController.Analyze(JSONDataStr, connectedSocketWithAndroid);
            }
            log.info("ListeningToAndroid Thread의 끝");
        } catch (IOException e) {
            log.error("ListeningToAndroid Thread의 run()에서 에러발생");
            e.printStackTrace();
        }
    }
}
