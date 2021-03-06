package com.team555.inu.ringmybell_mainserver.server.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

@Setter
@Getter
@Slf4j
@NoArgsConstructor
public class StoredRasberryPi extends RasberryPi{

    // 연결된 Socket 객체
    private Socket socket;
    // 연결된 소켓의 BufferedWriter
    private BufferedWriter bufferedWriter;
    // 최근 정류장 고유번호
    private String recentStopIdentifier;
    // 최근 정류장 이름
    private String recentStopName;
    // 가장 최근 null이 아닌 정류장 고유번호
    private String recentNotNullStopIdentifier;
    // 가장 최근 null이 아닌 정류장 이름
    private String recentNotNullStopName;
    // 현재 운행 방향
    private String recentDirection;

    public StoredRasberryPi(RasberryPi rasberryPi, Socket socket) {
        super(rasberryPi.getBusNumPlate(), rasberryPi.getRouteNum(), rasberryPi.getLat(), rasberryPi.getLon());

        this.socket = socket;

        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.recentStopIdentifier = null;
        this.recentStopName = null;
        this.recentNotNullStopIdentifier = null;
        this.recentNotNullStopName = null;
        this.recentDirection = null;

        log.info("StoredRasberryPi객체 생성 완료");
    }
}
