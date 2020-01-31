package com.team555.inu.ringmybell_mainserver.server.control;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team555.inu.ringmybell_mainserver.server.service.*;
import com.team555.inu.ringmybell_mainserver.server.vo.Android;
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

    private final RequestBusRouteService requestBusRouteService;

    public DataAnalyzeController(ConfirmBusService confirmBusService,
                                 AddReservationService addReservationService,
                                 UpdateReservationService updateReservationService,
                                 DeleteReservationService deleteReservationService,
                                 RequestBusRouteService requestBusRouteService) {
        this.addReservationService = addReservationService;
        this.updateReservationService = updateReservationService;
        this.deleteReservationService = deleteReservationService;
        this.requestBusRouteService = requestBusRouteService;
        this.objectMapper = new ObjectMapper();
        this.confirmBusService = confirmBusService;
    }

    // 안드로이드 클라이언트로부터 받은 JSON String 데이터를 분석하는 함수
    public void Analyze(String JSONDataStr, Socket socket){

        log.info("DataAnalyzeController에 데이터 전달됨 : " + JSONDataStr);

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
//            case "ringImmediately":
//                log.info("ringImmediately요청 도착");
//                // 안드로이드 클라이언트에서 즉시 벨 작동 요청
//
//                // 데이터 형태 : {"ringImmediately", Reservation객체}
//
//                // Reservation 객체의 하차예약 정류장은 의미없음
//                // 안드로이드 식별자와 탑승 버스 차량번호만 필요
//
//                // 라즈베리파이에게 버스벨 울림 신호 보냄
//
//                // 벨 작동을 요청한 사람이 만약
//                // 데이터베이스에 존재하는 기존 예약자라면,
//                // database에서 예약정보 삭제
//                // 있든 없든 그냥 무조건 삭제하면 됨
//                // 없으면 삭제처리 없이 진행됨
//
//                Reservation reservation = objectMapper.convertValue(hashMapData.get(key), Reservation.class);
//
//                // 즉시 버스 벨 작동
//                currentBuses.ringBellImmediately(reservation.getAndroidClientIdentifier(), reservation.getBusNumPlate());
//
//                // 즉시 벨 작동 요청자의 예약정보 삭제
//                try {
//                    deleteReservationImmediately.deleteReservation(reservation.getAndroidClientIdentifier());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                log.info("ringImmediatley 실행 완료");
//
//                break;
            case "requestBusRoute":
                // 사전 예약시 버스 노선 출력을 위한 버스 노선 요청
                // 요청 데이터 형태 : {"requestBusRoute","780-1"};
                log.info("requestBusRoute 요청 도착");

                // requestBusRouteService로 Android객체, socket 넘김.
                requestBusRouteService.run(objectMapper.convertValue(hashMapData.get(key), String.class), socket);

                break;
//            case "rasberryPiBusInform":
//                // 라즈베리파이에서 소켓 접속 직후,
//                // 해당 라즈베리파이가 설치되어있는 버스 정보를 전송함
//                // 서버는 이 정보를
//                // {"버스차량번호":BufferedWriter}로 저장했다가, 버스 벨 작동시 사용해야 함
//                // {"rasberryPiBusInform":Bus객체} 형태의 JSON String 데이터
//                // 이 버스객체는 차량번호, 노선번호를 갖고있음
//                // 버스 객체를 소켓 객체와 함께 전달
//
//                currentBuses.registerNewBus(socket, objectMapper.convertValue(hashMapData.get(key), String.class));
//
//                break;
//            case "busGPSInform":
//                // 계속 실시간으로 모든 라즈베리파이로부터 버스 GPS정보 수신
//                // 데이터 형태 : {"busGPSInform:{"버스차량번호":"인천11가2222",routeNum:"780-1번",lat:37.398375,lon:126.672892}}
//                // 실수는 double형
//                // 데이터 형태 : {"busGPSInform:BusGPSInform객체}
//                log.info("busGPSInform 정보 도착");
//
//                // BusGPSInform 객체만 꺼내서 전달
//                receiveBusGPSService.updateBusGPS(
//                        objectMapper.convertValue(hashMapData.get(key), BusGPSInform.class),
//                        currentBuses,
//                        busStopLocationsDao,
//                        deleteReservations);
//                break;
            default:
                log.error("DataAnalyzeController의 switch(key)에서 key값이 default로 넘어감");
        }
    }
}
