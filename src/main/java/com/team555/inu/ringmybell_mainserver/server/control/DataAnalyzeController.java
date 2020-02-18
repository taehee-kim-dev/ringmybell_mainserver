package com.team555.inu.ringmybell_mainserver.server.control;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team555.inu.ringmybell_mainserver.server.service.*;
import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.RasberryPi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

// 안드로이드 클라이언트로부터 받은 JSON String 데이터를 분석하는 객체
@Slf4j
@Controller
public class DataAnalyzeController {

    // JSON 데이터를 처리할 ObjectMapper객체를 참조할 참조변수
    private final ObjectMapper objectMapper;

    private final ConfirmBusService confirmBusService;
    private final AddReservationService addReservationService;
    private final UpdateReservationService updateReservationService;
    private final DeleteReservationService deleteReservationService;
    private final RingImmediatelyService ringImmediatelyService;
    private final SearchBusRoutesService searchBusRoutesService;
    private final RequestBusRouteService requestBusRouteService;

    private final RegisterBusService registerBusService;
    private final BusGPSInformService busGPSInformService;

    public DataAnalyzeController(ConfirmBusService confirmBusService,
                                 AddReservationService addReservationService,
                                 UpdateReservationService updateReservationService,
                                 DeleteReservationService deleteReservationService,
                                 RingImmediatelyService ringImmediatelyService,
                                 SearchBusRoutesService searchBusRoutesService,
                                 RequestBusRouteService requestBusRouteService,
                                 RegisterBusService registerBusService,
                                 BusGPSInformService busGPSInformService) {
        this.ringImmediatelyService = ringImmediatelyService;
        this.registerBusService = registerBusService;
        this.busGPSInformService = busGPSInformService;
        this.objectMapper = new ObjectMapper();
        this.confirmBusService = confirmBusService;
        this.addReservationService = addReservationService;
        this.updateReservationService = updateReservationService;
        this.deleteReservationService = deleteReservationService;
        this.searchBusRoutesService = searchBusRoutesService;
        this.requestBusRouteService = requestBusRouteService;
    }

    // 안드로이드 클라이언트로부터 받은 JSON String 데이터를 분석하는 함수
    public void Analyze(String JSONDataStr, Socket socket){

        HashMap<String, Object> hashMapData = null;

        // JSON String Data를 HashMap객체로 변환
        try {
            hashMapData = objectMapper.readValue(JSONDataStr, new TypeReference<HashMap<String, Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        // HashMap의 key값들을 담을 Set 객체 생성
        Set set = hashMapData.keySet();
        // Set의 key값들을 탐색할 Iterator 객체 생성
        Iterator iterator = set.iterator();

        // HashMap에서 key값 추출
        // 한 개일 것임.
        String key = null;
        while(iterator.hasNext()){
            key = (String)iterator.next();
        }

        log.info("key : " + key);

        switch (key){
            case "confirmBus":
                // 안드로이드 클라이언트에서 현재 탑승 버스 확정
                log.info("confirmBus 요청 도착");

                // confirmBusService로 Android객체, socket 넘김.
                confirmBusService.run(objectMapper.convertValue(hashMapData.get(key), Android.class), socket);

                break;
            case "addReservation":
                // 하차예약 처리
                log.info("addReservation 요청 도착");

                addReservationService.run(objectMapper.convertValue(hashMapData.get(key), Android.class));

                break;
            case "updateReservation":
                // 예약 업데이트 처리
                log.info("updateReservation 요청 도착");

                updateReservationService.run(objectMapper.convertValue(hashMapData.get(key), Android.class));

                break;
            case "deleteReservation":
                // 예약 삭제 처리
                log.info("deleteReservation 요청 도착");

                deleteReservationService.run(objectMapper.convertValue(hashMapData.get(key), Android.class));

                break;
            case "ringImmediately":
                log.info("ringImmediately요청 도착");
                // 안드로이드 클라이언트에서 즉시 벨 작동 요청

                // 데이터 형태 : {"ringImmediately", Android객체}
                ringImmediatelyService.run(objectMapper.convertValue(hashMapData.get(key), Android.class));

                break;
            case "searchBusRoute":
                // 사전예약시 버스 노선 검색 요청
                log.info("searchBusRoute 요청 도착");

                // searchBusRouteService로 Android객체, socket 넘김.
                searchBusRoutesService.run(objectMapper.convertValue(hashMapData.get(key), Android.class), socket);

                break;
            case "requestBusRoute":
                // 사전 예약시 버스 노선 출력을 위한 버스 노선 요청
                // 요청 데이터 형태 : {"requestBusRoute","780-1"};
                log.info("requestBusRoute 요청 도착");

                // requestBusRouteService로 Android객체, socket 넘김.
                requestBusRouteService.run(objectMapper.convertValue(hashMapData.get(key), Android.class), socket);

                break;
            case "registerBus":
                // 라즈베리파이에서 소켓 접속 직후, 버스 등록 요청
                log.info("registerBus 요청 도착");

                // 중앙 저장용 arrayList에 StoredRasberryPi 저장
                registerBusService.run(objectMapper.convertValue(hashMapData.get(key), RasberryPi.class), socket);

                break;
            case "busGPSInform":
                // 계속 실시간으로 모든 라즈베리파이로부터 버스 GPS정보 수신
                // 데이터 형태 : {"busGPSInform:{busNumPlate:"인천11가2222",routeNum:"780-1번",lat:37.398375,lon:126.672892}}
                // 실수는 double형
                // 데이터 형태 : {"busGPSInform:RasberryPi객체}
                log.info("busGPSInform 정보 도착");

                busGPSInformService.run(objectMapper.convertValue(hashMapData.get(key), RasberryPi.class));

                break;
            default:
                log.error("DataAnalyzeController의 switch(key)에서 key값이 default로 넘어감");
        }
    }
}
