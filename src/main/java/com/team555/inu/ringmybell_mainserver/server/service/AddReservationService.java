package com.team555.inu.ringmybell_mainserver.server.service;

import com.team555.inu.ringmybell_mainserver.server.dao.AddReservationDao;
import com.team555.inu.ringmybell_mainserver.server.sockets.AndroidSockets;
import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddReservationService {

    private final AddReservationDao addReservationDao;
    private final AndroidSockets androidSockets;

    public void run(Android android){
        int result = addReservationDao.run(android);

        if(result == 1){
            log.info("addreservation 성공!!");
            androidSockets.updateStoredAndroid(android);
        }else{
            log.error("addReservation 실패");
            log.error("result : " + result);
        }

    }
}
