package com.team555.inu.ringmybell_mainserver.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team555.inu.ringmybell_mainserver.server.sockets.RasberryPiSockets;
import com.team555.inu.ringmybell_mainserver.server.vo.RasberryPi;
import com.team555.inu.ringmybell_mainserver.server.vo.StoredRasberryPi;
import org.springframework.stereotype.Service;

import java.net.Socket;

@Service
public class RegisterBusService {

    // JSON 데이터를 처리할 ObjectMapper객체를 참조할 참조변수
    private final ObjectMapper objectMapper;
    private final RasberryPiSockets rasberryPiSockets;

    public RegisterBusService(RasberryPiSockets rasberryPiSockets) {
        this.objectMapper = new ObjectMapper();
        this.rasberryPiSockets = rasberryPiSockets;
    }

    public void run(RasberryPi rasberryPi, Socket socket){

        // 중앙 저장용 arrayList에 StoredRasberryPi 저장
        rasberryPiSockets.addStoredRasberryPi(new StoredRasberryPi(rasberryPi, socket));
    }
}
