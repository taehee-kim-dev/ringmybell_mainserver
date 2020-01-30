package com.team555.inu.ringmybell_mainserver.server.sockets;

import com.team555.inu.ringmybell_mainserver.server.vo.StoredAndroid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
public class AndroidSockets {

    private ArrayList<StoredAndroid> listOfStoredAndroid;

    public AndroidSockets() {
        listOfStoredAndroid = new ArrayList<>();
    }

    public void addStoredAndroid(StoredAndroid storedAndroid){
        listOfStoredAndroid.add(storedAndroid);

        log.info("listOfStoredAndroid에 StoredAndroid 추가 완료");

        log.info("현재 저장되어있는 StoredAndroid객체 : ");
        for(StoredAndroid sa : listOfStoredAndroid){
            log.info("identifier : " + sa.getIdentifier());
            log.info("busNumPlate : " + sa.getBusNumPlate());
            log.info("routeNum : " + sa.getRouteNum());
            log.info("stopToBook : " + sa.getStopToBook());
        }
    }
}
