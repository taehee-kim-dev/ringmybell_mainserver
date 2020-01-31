package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.BusStop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RingMyBellMapper {
    // 780-1번 버스의 정류장 정보를 데이터베이스에서 검색하여 BusStop 객체 List로 반환
    List<BusStop> selectBusStopsListOf780_1() throws Exception;
    // 예약 추가
    int addReservation(Android android) throws Exception;
    // 예약 업데이트
    int updateReservation(Android android) throws Exception;
    // 예약 삭제
    int deleteReservation(Android android) throws Exception;
}
