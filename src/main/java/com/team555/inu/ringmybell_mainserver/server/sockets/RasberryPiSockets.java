package com.team555.inu.ringmybell_mainserver.server.sockets;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.RasberryPi;
import com.team555.inu.ringmybell_mainserver.server.vo.StoredRasberryPi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;

@Slf4j
@Component
public class RasberryPiSockets {

    private ArrayList<StoredRasberryPi> listOfStoredRasberryPi;

    public RasberryPiSockets() {
        listOfStoredRasberryPi = new ArrayList<>();
    }

    public BufferedWriter getBufferedWriter(RasberryPi rasberryPi){
        BufferedWriter result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
                result = storedRasberryPi.getBufferedWriter();
                break;
            }
        }
        return result;
    }

    public void addStoredRasberryPi(StoredRasberryPi storedRasberryPi){

        for(StoredRasberryPi sr : listOfStoredRasberryPi){
            // 만약 기존에 같은 차량번호의 객체가 없다면
            if(sr.getBusNumPlate().equals(storedRasberryPi.getBusNumPlate())){
                listOfStoredRasberryPi.add(storedRasberryPi);

                log.info("listOfStoredRasberryPi에 StoredRasberryPi 추가 완료");
                showAllRasberryPiSockets();
            }
        }
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

    public void updateStoredRasberryPi(RasberryPi rasberryPi, String checkedCurrentStop){
        log.info("StoredRasberryPi의 현재 위치 업데이트");

        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
                storedRasberryPi.setLat(rasberryPi.getLat());
                storedRasberryPi.setLon(rasberryPi.getLon());
                storedRasberryPi.setRecentStop(checkedCurrentStop);
                if(checkedCurrentStop != null){
                    storedRasberryPi.setRecentNotNullStop(checkedCurrentStop);
                }
                log.info("StoredRasberryPi 업데이트 완료");
                break;
            }
        }

        showAllRasberryPiSockets();

    }

    public String getRecentStopByRasberryPi(RasberryPi rasberryPi){
        String result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
                result = storedRasberryPi.getRecentStop();
                break;
            }
        }
        return result;
    }

    public String getRecentNotNullStopByRasberryPi(RasberryPi rasberryPi){
        String result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
                result = storedRasberryPi.getRecentNotNullStop();
                break;
            }
        }
        return result;
    }

    public String getRecentStopByAndroid(Android android){
        String result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(android.getBusNumPlate())){
                result = storedRasberryPi.getRecentStop();
                break;
            }
        }
        return result;
    }

    public String getRecentNotNullStopByAndroid(Android android){
        String result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(android.getBusNumPlate())){
                result = storedRasberryPi.getRecentNotNullStop();
                break;
            }
        }
        return result;
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
            log.info("recentStop : " + sr.getRecentStop());
            log.info("recentNotNullStop : " + sr.getRecentNotNullStop() + "\n");
        }
    }
}
