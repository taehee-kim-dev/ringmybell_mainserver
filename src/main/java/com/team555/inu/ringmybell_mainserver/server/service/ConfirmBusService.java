package com.team555.inu.ringmybell_mainserver.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team555.inu.ringmybell_mainserver.server.dao.RingMyBellMapper;
import com.team555.inu.ringmybell_mainserver.server.networking.android.SendOnceToAndroid;
import com.team555.inu.ringmybell_mainserver.server.sockets.AndroidSockets;
import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.BusLocation;
import com.team555.inu.ringmybell_mainserver.server.vo.BusStop;
import com.team555.inu.ringmybell_mainserver.server.vo.StoredAndroid;
import org.springframework.stereotype.Service;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;

@Service
public class ConfirmBusService {

    // JSON 데이터를 처리할 ObjectMapper객체를 참조할 참조변수
    private final ObjectMapper objectMapper;
    private final RingMyBellMapper ringMyBellMapper;
    private final SendOnceToAndroid sendOnceToAndroid;
    private final AndroidSockets androidSockets;

    public ConfirmBusService(RingMyBellMapper ringMyBellMapper,
                             SendOnceToAndroid sendOnceToAndroid,
                             AndroidSockets androidSockets) {
        this.sendOnceToAndroid = sendOnceToAndroid;
        this.ringMyBellMapper = ringMyBellMapper;
        this.androidSockets = androidSockets;
        objectMapper = new ObjectMapper();
    }

    public void run(Android android, Socket socket){

        // 해당 차량 노선의 노선정류장 BusStop객체 List를 HashMap에 담아 SendOnceToAndroid로 보냄
        // {"busRoute", List<BusStop객체>}
        // 위와 같이 담을 HashMap 객체를 생성하고,
        HashMap<String, List<BusStop>> resultHashMap1 = new HashMap<>();

        // 데이터베이스에서 android객체의 routeNum으로 노선정보를 검색하여 List<BusStop>형태로 결과를 받아냄.

        String routeNum = android.getRouteNum();
        List<BusStop> listOfBusStops = null;

        if(routeNum.equals("780-1")){
            try {
                listOfBusStops = ringMyBellMapper.selectBusStopsListOf780_1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 결과 HashMap 제작
        resultHashMap1.put("busRoute", listOfBusStops);

        String json1 = null;
        // JSON 문자열로 변환
        try {
            json1 = objectMapper.writeValueAsString(resultHashMap1);
            System.out.println(json1);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }

        sendOnceToAndroid.run(socket, json1);

        // 그리고 직후에, "{"busLocation : BusLocation객체}"를 한 번만 조건 없이 보내준다.

        // 해당 차량에 대한 BusLocation객체를 HashMap에 담아 SendOnceToAndroid로 보냄
        // {"busLocation", BusLocation객체}
        // 위와 같이 담을 HashMap 객체를 생성하고,
        HashMap<String, BusLocation> resultHashMap2 = new HashMap<>();

        BusLocation busLocation = new BusLocation();
        busLocation.setRecentNotNullStop("testRecentNotNullStop");
        busLocation.setCurrentStop("38-378_test");

        // 결과 HashMap 제작
        resultHashMap2.put("busLocation", busLocation);

        String json2 = null;
        // JSON 문자열로 변환
        try {
            json2 = objectMapper.writeValueAsString(resultHashMap2);
            System.out.println(json2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        sendOnceToAndroid.run(socket, json2);

        /*
            Android객체를 상속받아 확장된 StoredAndroid 객체를 생성.
            여기에는 Android객체의 전체정보와 접속 소켓 객체, BufferedWriter가 추가됨.
            stopToBook 필드는 "Initial_value" 값으로 설정.
            이 StoredAndroid를 중앙 저장용 ArrayList에 저장.
        */

        androidSockets.addStoredAndroid(new StoredAndroid(android, socket));

    }
}
