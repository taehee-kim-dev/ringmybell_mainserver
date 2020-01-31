package com.team555.inu.ringmybell_mainserver.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team555.inu.ringmybell_mainserver.server.dao.SearchBusStopsListDao;
import com.team555.inu.ringmybell_mainserver.server.networking.android.SendOnceToAndroid;
import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.BusLocation;
import com.team555.inu.ringmybell_mainserver.server.vo.BusStop;
import com.team555.inu.ringmybell_mainserver.server.vo.StoredAndroid;
import org.springframework.stereotype.Service;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;

@Service
public class RequestBusRouteService {

    // JSON 데이터를 처리할 ObjectMapper객체를 참조할 참조변수
    private final ObjectMapper objectMapper;
    private final SearchBusStopsListDao searchBusStopsListDao;
    private final SendOnceToAndroid sendOnceToAndroid;

    public RequestBusRouteService(SearchBusStopsListDao searchBusStopsListDao,
                                  SendOnceToAndroid sendOnceToAndroid) {
        this.objectMapper = new ObjectMapper();
        this.searchBusStopsListDao = searchBusStopsListDao;
        this.sendOnceToAndroid = sendOnceToAndroid;
    }

    public void run(String requestedRouteNum, Socket socket){

        // 해당 차량 노선의 노선정류장 BusStop객체 List를 HashMap에 담아 SendOnceToAndroid로 보냄
        // {"respondBusRoute", List<BusStop객체>}
        // 위와 같이 담을 HashMap 객체를 생성하고,
        HashMap<String, List<BusStop>> resultHashMap = new HashMap<>();

        // 데이터베이스에서 requestedRouteNum으로 노선정보를 검색하여 List<BusStop>형태로 결과를 받아냄.
        List<BusStop> listOfBusStops = searchBusStopsListDao.run(requestedRouteNum);

        // 결과 HashMap 제작
        resultHashMap.put("respondBusRoute", listOfBusStops);

        String json = null;
        // JSON 문자열로 변환
        try {
            json = objectMapper.writeValueAsString(resultHashMap);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }

        sendOnceToAndroid.run(socket, json);

    }
}
