package com.team555.inu.ringmybell_mainserver.server.service;

import com.team555.inu.ringmybell_mainserver.server.dao.BusArrivedDao;
import com.team555.inu.ringmybell_mainserver.server.dao.CheckBusLocationDao;
import com.team555.inu.ringmybell_mainserver.server.networking.android.SendRepeatedlyToAndroid;
import com.team555.inu.ringmybell_mainserver.server.networking.rasberrypi.RingBellToRasberryPi;
import com.team555.inu.ringmybell_mainserver.server.sockets.AndroidSockets;
import com.team555.inu.ringmybell_mainserver.server.sockets.RasberryPiSockets;
import com.team555.inu.ringmybell_mainserver.server.vo.*;
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

        // 데이터베이스에서 조회된 버스 현재 위치 정보
        CheckedBusLocation checkedBusLocation = checkBusLocationDao.run(rasberryPi);

        String checkedCurrentStop = null;
        // 이전 정류장 고유번호
        String beforeStopIdentifier = rasberryPiSockets.getRecentStopIdentifierByRasberryPi(rasberryPi);
        String beforeStopName = rasberryPiSockets.getRecentStopNameByRasberryPi(rasberryPi);

        if(checkedBusLocation == null){
            log.info("버스 현재위치 반경 50m 내에 정류장 없음.");
            checkedBusLocation = new CheckedBusLocation(null, null, null, false);
            // StoredRasberryPi 업데이트
            rasberryPiSockets.updateStoredRasberryPi(rasberryPi, checkedBusLocation);
        }else{

            /*
             *   현재 데이터베이스에서 조회된 위치정보에서,
             *   운행방향이 기존 버스의 운행방향과 다를 때,
             *   만약 현재 위치가 방향세팅 정류장이 아니라면, 이 버스위치정보 수신을 무시한다.
             * */

            if(!checkedBusLocation.getDirection().equals(rasberryPiSockets.getRecentDirectionByRasberryPi(rasberryPi))){
                if(checkedBusLocation.getDirection_setting() == false){
                    log.info("버스가 현재 가장 가까이 있는 정류장이 설정된 운행방향과 상이하여, 이 정보 무시.");

                    log.info("버스 현재위치 반경 50m 내에 정류장 존재.");
                    log.info(rasberryPi.getBusNumPlate() + "의 현재 위치 : ");
                    log.info("stop_name : " + checkedBusLocation.getStop_name());
                    log.info("stop_identifier : " + checkedBusLocation.getStop_identifier());
                    log.info("direction : " + checkedBusLocation.getDirection());
                    log.info("direction_setting : " + checkedBusLocation.getDirection_setting());
                    return;
                }

            }


            log.info("버스 현재위치 반경 50m 내에 정류장 존재.");
            log.info(rasberryPi.getBusNumPlate() + "의 현재 위치 : ");
            log.info("stop_name : " + checkedBusLocation.getStop_name());
            log.info("stop_identifier : " + checkedBusLocation.getStop_identifier());
            log.info("direction : " + checkedBusLocation.getDirection());
            log.info("direction_setting : " + checkedBusLocation.getDirection_setting());

            // StoredRasberryPi 업데이트
            rasberryPiSockets.updateStoredRasberryPi(rasberryPi, checkedBusLocation);
            checkedCurrentStop = checkedBusLocation.getStop_identifier();
        }


        // 정류장의 50m 반경 밖에서 안으로 들어가거나,
        // 안에서 밖으로 빠져나올 때
        if(beforeStopName == null && checkedCurrentStop != null ||
                beforeStopName != null && checkedCurrentStop == null){
            /*
            * 해당 버스 차량번호에 탑승하고 있는 승객들의 Android 객체를 검색하여,
            * 그 객체에 있는 bufferedWriter 객체를 sendToAndroidRepeatedly에 넘기고,
            * BusLocation객체 또한 넘겨
            * 해당 버스 차량번호에 탑승하고 있는 승객들의 Android 클라이언트들에게 BusLocation객체를 보내준다.
            * */

            if(beforeStopName == null && checkedCurrentStop != null){
                log.info(checkedBusLocation.getStop_name() + "(으)로 진입");
                log.info("운행 방향 : " + checkedBusLocation.getDirection());
            }

            if(beforeStopName != null && checkedCurrentStop == null){
                log.info(beforeStopName + "에서 빠져나옴");

                int deletedReservations = busArrivedDao.run(new BusArriveInform(rasberryPi.getBusNumPlate(), beforeStopIdentifier));
                log.info("deletedReservations : " + deletedReservations);

                // deletedReservations값이 1이상이면 현재 버스의 벨 울림
                if(deletedReservations > 0){
                    ringBellToRasberryPi.run(rasberryPiSockets.getBufferedWriter(rasberryPi));
                }
            }

            log.info("안드로이드에게 BusLocation 객체 전송");
            log.info("currentStop : " + checkedCurrentStop);
            log.info("recentNotNullStop : " + rasberryPiSockets.getRecentNotNullStopIdentifierByRasberryPi(rasberryPi));

            for(StoredAndroid storedAndroid : androidSockets.getListOfStoredAndroid()){
                if(storedAndroid.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
                    sendRepeatedlyToAndroid.run(storedAndroid.getBufferedWriter(), new BusLocation(checkedCurrentStop, rasberryPiSockets.getRecentNotNullStopIdentifierByRasberryPi(rasberryPi)));
                }
            }

            log.info("========================================>");
        }

    }
}
