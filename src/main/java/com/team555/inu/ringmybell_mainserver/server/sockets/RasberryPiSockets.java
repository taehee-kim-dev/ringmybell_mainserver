package com.team555.inu.ringmybell_mainserver.server.sockets;

import com.team555.inu.ringmybell_mainserver.server.vo.RasberryPi;
import com.team555.inu.ringmybell_mainserver.server.vo.StoredRasberryPi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.ArrayList;

@Slf4j
@Component
public class RasberryPiSockets {

    private ArrayList<StoredRasberryPi> listOfStoredRasberryPi;

    public RasberryPiSockets() {
        listOfStoredRasberryPi = new ArrayList<>();
    }

    public void addStoredRasberryPi(StoredRasberryPi storedRasberryPi){
        listOfStoredRasberryPi.add(storedRasberryPi);

        log.info("listOfStoredRasberryPi에 StoredRasberryPi 추가 완료");
        showAllRasberryPiSockets();
    }

    public void deleteStoredRasberryPi(Socket socket){
        log.info("ArrayList에서 StoredRasberryPi 삭제");
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getSocket().equals(socket)){
                listOfStoredRasberryPi.remove(storedRasberryPi);
                log.info("ArrayList에서 StoredRasberryPi 삭제 완료");
                break;
            }
        }
        showAllRasberryPiSockets();
    }

    public void updateStoredRasberryPi(RasberryPi rasberrypi){
        log.info("StoredRasberryPi의 현재 위치 업데이트");

        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(rasberrypi.getBusNumPlate())){
                storedRasberryPi.setLat(storedRasberryPi.getLat());
                storedRasberryPi.setLon(storedRasberryPi.getLon());
                log.info("StoredRasberryPi의 stopToBook 업데이트 완료");
                break;
            }
        }
        showAllRasberryPiSockets();
    }

    private void showAllRasberryPiSockets(){
        log.info("현재 저장되어있는 StoredRasberryPi객체 : \n");
        for(StoredRasberryPi sr : listOfStoredRasberryPi){
            log.info("busNumPlate : " + sr.getBusNumPlate());
            log.info("routeNum : " + sr.getRouteNum());
            log.info("lat : " + sr.getLat());
            log.info("lon : " + sr.getLon());
            log.info("socket : " + sr.getSocket());
            log.info("bufferedWriter : " + sr.getBufferedWriter());
            log.info("currentStop : " + sr.getCurrentStop());
            log.info("beforeStop : " + sr.getBeforeStop());
            log.info("recentNotNullStop : " + sr.getRecentNotNullStop() + "\n");
        }
    }
}
