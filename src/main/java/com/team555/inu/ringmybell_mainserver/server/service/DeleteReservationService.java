package com.team555.inu.ringmybell_mainserver.server.service;

import com.team555.inu.ringmybell_mainserver.server.dao.DeleteReservationDao;
import com.team555.inu.ringmybell_mainserver.server.dao.UpdateReservationDao;
import com.team555.inu.ringmybell_mainserver.server.sockets.AndroidSockets;
import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeleteReservationService {
    private final DeleteReservationDao deleteReservationDao;
    private final AndroidSockets androidSockets;

    public void run(Android android){
        int result = deleteReservationDao.run(android);

        if(result == 1){
            log.info("deleteReservation 성공!!");
            Android tmpAndroid = android;
            tmpAndroid.setStopToBook("none");
            androidSockets.updateStoredAndroid(tmpAndroid);
        }else{
            log.error("deleteReservation 실패");
            log.error("result : " + result);
        }

    }
}
