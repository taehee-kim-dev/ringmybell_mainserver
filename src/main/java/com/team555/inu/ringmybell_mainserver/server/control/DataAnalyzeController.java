package com.team555.inu.ringmybell_mainserver.server.control;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team555.inu.ringmybell_mainserver.server.service.ConfirmBusService;
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
    private ObjectMapper objectMapper;

    private ConfirmBusService confirmBusService;

    public DataAnalyzeController(ConfirmBusService confirmBusService) {
        this.objectMapper = new ObjectMapper();
        this.confirmBusService = confirmBusService;
    }

    // 안드로이드 클라이언트로부터 받은 JSON String 데이터를 분석하는 함수
    public void Analyze(String JSONDataStr, Socket socket){

        log.info("DataAnalyzeController에 데이터 전달됨 : " + JSONDataStr);

        HashMap<String, Object> HashMapData = null;

        // JSON String Data를 HashMap객체로 변환
        try {
            HashMapData = objectMapper.readValue(JSONDataStr, new TypeReference<HashMap<String, Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        // HashMap의 key값들을 담을 Set 객체 생성
        Set set = HashMapData.keySet();
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

                // JSON 데이터에서 Android객체 꺼냄
                Android android = objectMapper.convertValue(HashMapData.get(key), Android.class);

                // confirmBusService로 Android객체, socket 넘김.
                confirmBusService.run(android, socket);

                break;
//            case "addReservation":
//                // 하차예약 처리
//                // database에 예약정보를 저장하고
//                // 안드로이드에게 탑승 버스의 현재위치 실시간 전송
//                // 하차 정류장에 도착하면 예약정보 삭제
//
//                System.out.println("addReservation 요청 도착");
//
//                // AddReservation로 데이터베이스에 예약정보 저장
//                try {
//                    reservationDao.addReservation(objectMapper.convertValue(hashMapData.get(key), Reservation.class));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("addReservation 처리 완료");
//
//                break;
//            case "updateReservation":
//                // 예약 업데이트 처리
//                System.out.println("updateReservation 요청 도착");
//
//                // UpdateReservation로 데이터베이스에 예약정보 업데이트
//                try {
//                    reservationDao.updateReservation(objectMapper.convertValue(hashMapData.get(key), Reservation.class));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("updateReservation 처리 완료");
//
//
//                break;
//            case "deleteReservation":
//                // 예약 삭제 처리
//                System.out.println("deleteReservation 요청 도착");
//                // deleteReservation 요청에서
//                // HashMap의 Obejct는 String(androidClientIdentifier)이다.
//                // DeleteReservation실행
//
//                // deleteReservation으로 데이터베이스에서 정보 삭제
//                try {
//                    reservationDao.deleteReservation(objectMapper.convertValue(hashMapData.get(key), String.class));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("deleteReservation 처리 완료");
//
//                break;
//            case "ringImmediately":
//                System.out.println("ringImmediately요청 도착");
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
//                System.out.println("ringImmediatley 실행 완료");
//
//                break;
//            case "requestBusRoute":
//                // 사전 예약시 버스 노선 출력을 위한 버스 노선 요청
//                // 요청 데이터 형태 : {"requestBusRoute","780-1번"};
//                System.out.println("requestBusRoute 요청 도착");
//                // JSON 데이터에서 노선 번호 꺼냄
//                String routeNum = objectMapper.convertValue(hashMapData.get(key), String.class);
//
//                // 해당 차량 노선의 노선정류장 ArrayList를 HashMap에 담아 SendingToAndroid2로 보냄
//                // {"respondBusRoute", ArrayList<ArrayList<"정류장이름", "정류장고유번호">>}
//                // 위와 같이 담을 HashMap 객체를 생성하고,
//                HashMap<String, ArrayList<ArrayList<String>>> busRoute2 = new HashMap<>();
//                // routeNum의  버스 노선번호로 서버 시작시 데이터베이스에서 업로드된
//                // 노선별 정류장 이중리스트를 찾아 "busRoute"라는 key로 넣는다
//                busRoute2.put("respondBusRoute", BusStops.listOfStopsByRoute.get(routeNum));
//
//                String json2 = null;
//                // JSON 문자열로 변환
//                try {
//                    json2 = objectMapper.writeValueAsString(busRoute2);
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//
//                new Thread(new SendingToAndroid2(socket, json2)).start();
//                break;
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
//                System.out.println("busGPSInform 정보 도착");
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
