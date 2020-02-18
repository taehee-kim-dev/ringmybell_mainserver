package com.team555.inu.ringmybell_mainserver.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team555.inu.ringmybell_mainserver.server.dao.SearchBusRoutesListDao;
import com.team555.inu.ringmybell_mainserver.server.networking.android.SendOnceToAndroid;
import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.SearchedBusRoute;
import org.springframework.stereotype.Service;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;

@Service
public class SearchBusRoutesService {

    // JSON 데이터를 처리할 ObjectMapper객체를 참조할 참조변수
    private final ObjectMapper objectMapper;
    private final SearchBusRoutesListDao searchBusRoutesListDao;
    private final SendOnceToAndroid sendOnceToAndroid;

    public SearchBusRoutesService(SearchBusRoutesListDao searchBusRoutesListDao,
                                  SendOnceToAndroid sendOnceToAndroid) {
        this.objectMapper = new ObjectMapper();
        this.searchBusRoutesListDao = searchBusRoutesListDao;
        this.sendOnceToAndroid = sendOnceToAndroid;
    }

    public void run(Android android, Socket socket){

        // 해당 차량 노선의 노선정류장 SearchedBusRoute객체 List를 HashMap에 담아 SendOnceToAndroid로 보냄
        // {"resultBusRoutes", List<SearchedBusRoute객체>}
        // 위와 같이 담을 HashMap 객체를 생성하고,
        HashMap<String, List<SearchedBusRoute>> resultHashMap = new HashMap<>();

        // 데이터베이스에서 requestedRouteNum으로 노선정보를 검색하여 List<BusStop>형태로 결과를 받아냄.
        List<SearchedBusRoute> listOfSearchedBusRoutes = searchBusRoutesListDao.run(android);

        // 결과 HashMap 제작
        resultHashMap.put("resultBusRoutes", listOfSearchedBusRoutes);

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
