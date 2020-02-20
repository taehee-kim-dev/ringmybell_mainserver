package com.team555.inu.ringmybell_mainserver.server.sockets;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.CheckedBusLocation;
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
            if(sr.getBusNumPlate().equals(storedRasberryPi.getBusNumPlate())){
                // 기존에 같은 차량번호로 객체가 존재한다면 먼저 삭제
                listOfStoredRasberryPi.remove(sr);
                log.error("유효하지 않은 storedRasberryPi 객체가 존재하여 삭제함.");
                break;
            }
        }

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

    public void updateStoredRasberryPi(RasberryPi rasberryPi, CheckedBusLocation checkedBusLocation){
        log.info("StoredRasberryPi의 현재 위치 업데이트");

        // 라즈베리파이에서 온 rasberryPi 객체의 차량번호로
        // 저장되어있는 storedRasberryPi 객체 검색
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
                // 일단 위도, 경도, 현재 위치 정보는 무조건 저장 -> null 일 수도 있음.
                storedRasberryPi.setLat(rasberryPi.getLat());
                storedRasberryPi.setLon(rasberryPi.getLon());
                storedRasberryPi.setRecentStopIdentifier(checkedBusLocation.getStop_identifier()); // null일수도 아닐수도
                storedRasberryPi.setRecentStopName(checkedBusLocation.getStop_name()); // null일수도 아닐수도
                // 만약 현재 위치 정류장의 이름값이 null이 아니면, 
                // 버스 현재위치 반경 50m내에 정류장이 존재한다는 뜻이므로
                // not null 관련 필드 업데이트
                if(checkedBusLocation.getStop_identifier() != null){
                    storedRasberryPi.setRecentNotNullStopIdentifier(checkedBusLocation.getStop_identifier());
                    storedRasberryPi.setRecentNotNullStopName(checkedBusLocation.getStop_name());
                }
                // 현재 위치한 정류장이 방향 설정 정류장이라면,
                if(checkedBusLocation.getDirection_setting() == true){
                    storedRasberryPi.setRecentDirection(checkedBusLocation.getDirection());
                }
                log.info("StoredRasberryPi 업데이트 완료");
                break;
            }
        }

        showAllRasberryPiSockets();
    }

    public String getRecentDirectionByRasberryPi(RasberryPi rasberryPi){
        String result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
                result = storedRasberryPi.getRecentDirection();
                break;
            }
        }
        return result;
    }

    public String getRecentStopIdentifierByRasberryPi(RasberryPi rasberryPi){
        String result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
                result = storedRasberryPi.getRecentStopIdentifier();
                break;
            }
        }
        return result;
    }

    public String getRecentStopNameByRasberryPi(RasberryPi rasberryPi){
        String result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
                result = storedRasberryPi.getRecentStopName();
                break;
            }
        }
        return result;
    }

    public String getRecentNotNullStopIdentifierByRasberryPi(RasberryPi rasberryPi){
        String result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(rasberryPi.getBusNumPlate())){
                result = storedRasberryPi.getRecentNotNullStopIdentifier();
                break;
            }
        }
        return result;
    }

    public String getRecentStopIdentifierByAndroid(Android android){
        String result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(android.getBusNumPlate())){
                result = storedRasberryPi.getRecentStopIdentifier();
                break;
            }
        }
        return result;
    }

    public String getRecentNotNullStopIdentifierByAndroid(Android android){
        String result = null;
        for(StoredRasberryPi storedRasberryPi : listOfStoredRasberryPi){
            if(storedRasberryPi.getBusNumPlate().equals(android.getBusNumPlate())){
                result = storedRasberryPi.getRecentNotNullStopIdentifier();
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
            log.info("recentStopIdentifier : " + sr.getRecentStopIdentifier());
            log.info("recentStopName : " + sr.getRecentStopName());
            log.info("recentNotNullStopIdentifier : " + sr.getRecentNotNullStopIdentifier());
            log.info("recentNotNullStopName : " + sr.getRecentNotNullStopName());
            log.info("direction : " + sr.getRecentDirection() + "\n");
        }
    }
}
