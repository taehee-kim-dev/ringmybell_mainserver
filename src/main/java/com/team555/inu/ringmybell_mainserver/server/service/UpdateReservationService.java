package com.team555.inu.ringmybell_mainserver.server.service;

import com.team555.inu.ringmybell_mainserver.server.dao.UpdateReservationDao;
import com.team555.inu.ringmybell_mainserver.server.sockets.AndroidSockets;
import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateReservationService {
    private final UpdateReservationDao updateReservationDao;
    private final AndroidSockets androidSockets;

    public void run(Android android){
        int result = updateReservationDao.run(android);

        if(result == 1){
            log.info("updateReservation 성공!!");
            androidSockets.updateStoredAndroid(android);
        }else{
            log.error("addReservation 실패");
            log.error("result : " + result);
        }

    }
}
