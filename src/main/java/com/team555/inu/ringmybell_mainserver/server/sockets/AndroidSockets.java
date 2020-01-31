package com.team555.inu.ringmybell_mainserver.server.sockets;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.StoredAndroid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.Socket;
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
        showAllAndroidSockets();
    }

    public void deleteStoredAndroid(Socket socket){
        log.info("ArrayList에서 StoredAndroid 삭제");
        for(StoredAndroid storedAndroid : listOfStoredAndroid){
            if(storedAndroid.getSocket().equals(socket)){
                listOfStoredAndroid.remove(storedAndroid);
                log.info("ArrayList에서 StoredAndroid 삭제 완료");
                break;
            }
        }
        showAllAndroidSockets();
    }

    public void updateStoredAndroid(Android android){
        log.info("StoredAndroid의 stopToBook 업데이트");

        for(StoredAndroid storedAndroid : listOfStoredAndroid){
            if(storedAndroid.getIdentifier().equals(android.getIdentifier())){
                storedAndroid.setStopToBook(android.getStopToBook());
                log.info("StoredAndroid의 stopToBook 업데이트 완료");
                break;
            }
        }
        showAllAndroidSockets();
    }

    private void showAllAndroidSockets(){
        log.info("현재 저장되어있는 StoredAndroid객체 : \n");
        for(StoredAndroid sa : listOfStoredAndroid){
            log.info("identifier : " + sa.getIdentifier());
            log.info("busNumPlate : " + sa.getBusNumPlate());
            log.info("routeNum : " + sa.getRouteNum());
            log.info("stopToBook : " + sa.getStopToBook());
            log.info("socket : " + sa.getSocket());
            log.info("bufferedWriter : " + sa.getBufferedWriter() + "\n");
        }
    }
}
