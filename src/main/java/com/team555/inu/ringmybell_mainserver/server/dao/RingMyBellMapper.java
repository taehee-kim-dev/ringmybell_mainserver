package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RingMyBellMapper {
    // 780-1번 버스의 정류장 정보를 데이터베이스에서 검색하여 BusStop 객체 List로 반환
    List<BusStop> selectBusStopsListOf780_1() throws Exception;
    // 908번 버스의 정류장 정보를 데이터베이스에서 검색하여 BusStop 객체 List로 반환
    List<BusStop> selectBusStopsListOf908() throws Exception;
    // 예약 추가
    int addReservation(Android android) throws Exception;
    // 기존 예약 개수 확인
    int countReservation(Android android) throws Exception;
    // 예약 업데이트
    int updateReservation(Android android) throws Exception;
    // 예약 삭제
    int deleteReservation(Android android) throws Exception;
    // 버스노선 검색
    List<SearchedBusRoute> selectSearchedBusRoutesList(Android android) throws Exception;
    // 데이터베이스에 저장된 780-1번 정류장들의 GPS 데이터를 기반으로
    // 현재 버스의 위치를 확인함
    CheckedBusLocation checkBusLocationOf780_1(RasberryPi rasberryPi) throws Exception;
    // 현재 버스의 위치를 확인함
    CheckedBusLocation checkBusLocationOf908(RasberryPi rasberryPi) throws Exception;
    // 버스 도착시 예약정보 삭제
    int arriveAtTheBusStop(BusArriveInform busArriveInform) throws Exception;
}
