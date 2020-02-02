package com.team555.inu.ringmybell_mainserver.server.service;

import com.team555.inu.ringmybell_mainserver.server.dao.CheckBusLocationDao;
import com.team555.inu.ringmybell_mainserver.server.sockets.RasberryPiSockets;
import com.team555.inu.ringmybell_mainserver.server.vo.RasberryPi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BusGPSInformService {

    private final CheckBusLocationDao checkBusLocationDao;
    private final RasberryPiSockets rasberryPiSockets;

    public void run(RasberryPi rasberryPi){

        log.info("데이터베이스 결과값 : " + checkBusLocationDao.run(rasberryPi));

    }
}
