package com.team555.inu.ringmybell_mainserver.server.service;

import com.team555.inu.ringmybell_mainserver.server.dao.BusArrivedDao;
import com.team555.inu.ringmybell_mainserver.server.dao.CheckBusLocationDao;
import com.team555.inu.ringmybell_mainserver.server.networking.android.SendRepeatedlyToAndroid;
import com.team555.inu.ringmybell_mainserver.server.networking.rasberrypi.RingBellToRasberryPi;
import com.team555.inu.ringmybell_mainserver.server.sockets.AndroidSockets;
import com.team555.inu.ringmybell_mainserver.server.sockets.RasberryPiSockets;
import com.team555.inu.ringmybell_mainserver.server.vo.BusArriveInform;
import com.team555.inu.ringmybell_mainserver.server.vo.BusLocation;
import com.team555.inu.ringmybell_mainserver.server.vo.RasberryPi;
import com.team555.inu.ringmybell_mainserver.server.vo.StoredAndroid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BusGPSInformService {

    private final CheckBusLocationDao checkBusLocationDao;
    private final RasberryPiSockets rasberryPiSockets;
    private final BusArrivedDao busArrivedDao;
    private final AndroidSockets androidSockets;
    private final SendRepeatedlyToAndroid sendRepeatedlyToAndroid;
    private final RingBellToRasberryPi ringBellToRasberryPi;

    public void run(RasberryPi rasberryPi){

        String checkedCurrentStop = checkBusLocationDao.run(rasberryPi);
        String beforeStop = rasberryPiSockets.getRecentStop(rasberryPi);

        log.info(rasberryPi.getBusNumPlate() + "의 현재 위치 : " + checkedCurrentStop);

        // StoredRasberryPi 업데이트
        rasberryPiSockets.updateStoredRasberryPi(rasberryPi, checkedCurrentStop);


        // 정류장의 50m 반경 밖에서 안으로 들어가거나,
        // 안에서 밖으로 빠져나올 때
        if(beforeStop == null && checkedCurrentStop != null ||
            beforeStop != null && checkedCurrentStop == null){
            /*
            * 해당 버스 차량번호에 탑승하고 있는 승객들의 Android 객체를 검색하여,
            * 그 객체에 있는 bufferedWriter 객체를 sendToAndroidRepeatedly에 넘기고,
            * checkedCurrentStop, recentNotNullStop의 필드값을 갖고있는 BusLocation객체 또한 넘겨
            * 해당 버스 차량번호에 탑승하고 있는 승객들의 Android 클라이언트들에게 BusLocation객체를 보내준다.
            * */

            if(beforeStop == null && checkedCurrentStop != null){
                log.info(checkedCurrentStop + "으로 진입");
            }

            if(beforeStop != null && checkedCurrentStop == null){
                log.info(beforeStop + "에서 빠져나옴");

                int deletedReservations = busArrivedDao.run(new BusArriveInform(rasberryPi.getBusNumPlate(), beforeStop));
                log.info("deletedReservations : " + deletedReservations);

                // deletedReservations값이 1이상이면 현재 버스의 벨 울림
                if(deletedReservations > 0){
                    ringBellToRasberryPi.run(rasberryPiSockets.getBufferedWriter(rasberryPi));
                }
            }

            log.info("안드로이드에게 BusLocation 객체 전송");
            log.info("currentStop : " + checkedCurrentStop);
            log.info("recentNotNullStop : " + rasberryPiSockets.getRecentNotNullStop(rasberryPi));

//            for(StoredAndroid storedAndroid : androidSockets.getListOfStoredAndroid()){
//                if(storedAndroid.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
//                    sendRepeatedlyToAndroid.run(storedAndroid.getBufferedWriter(), new BusLocation(checkedCurrentStop, rasberryPiSockets.getRecentNotNullStop(rasberryPi)));
//                }
//            }
        }

    }
}
