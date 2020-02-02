package com.team555.inu.ringmybell_mainserver.server.service;

import com.team555.inu.ringmybell_mainserver.server.dao.DeleteReservationDao;
import com.team555.inu.ringmybell_mainserver.server.networking.rasberrypi.RingBellToRasberryPi;
import com.team555.inu.ringmybell_mainserver.server.sockets.AndroidSockets;
import com.team555.inu.ringmybell_mainserver.server.sockets.RasberryPiSockets;
import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.RasberryPi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RingImmediatelyService {

    private final DeleteReservationDao deleteReservationDao;
    private final AndroidSockets androidSockets;
    private final RasberryPiSockets rasberryPiSockets;
    private final RingBellToRasberryPi ringBellToRasberryPi;

    public void run(Android android){

        // 라즈베리파이에게 버스벨 울림 신호 보냄
        ringBellToRasberryPi.run(rasberryPiSockets.getBufferedWriter(new RasberryPi(android.getBusNumPlate(), "temp", 0.0, 0.0)));

        // 벨 작동을 요청한 사람이 만약
        // 데이터베이스에 존재하는 기존 예약자라면,
        // database에서 예약정보 삭제
        // 있든 없든 그냥 무조건 삭제하면 됨
        // 없으면 삭제처리 없이 진행됨
        int result = deleteReservationDao.run(android);

        if(result >= 0){
            log.info("deleteReservation 처리 완료!!");
            log.info("deletedReservation : " + result);
            Android tmpAndroid = android;
            tmpAndroid.setStopToBook("none");
            androidSockets.updateStoredAndroid(tmpAndroid);
        }else{
            log.error("deleteReservation 실패");
            log.error("result : " + result);
        }
    }
}
