package com.team555.inu.ringmybell_mainserver.server.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

// 안드로이드 객체
@Setter
@Getter
@Slf4j
@NoArgsConstructor
public class StoredAndroid extends Android {

    // 연결된 Socket 객체
    private Socket socket;
    // 연결된 소켓의 BufferedWriter
    private BufferedWriter bufferedWriter;

    public StoredAndroid(Android android, Socket socket) {
        super(android.getIdentifier(), android.getBusNumPlate(), android.getRouteNum(), "Initial_value");

        this.socket = socket;

        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("StoredAndroid객체 생성 완료");
    }
}
