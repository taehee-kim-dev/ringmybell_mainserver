package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.BusStop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MySqlDao {
    // 780-1번 버스의 정류장 정보를 데이터베이스에서 검색하여 BusStop 객체 List로 반환
    List<BusStop> selectBusStopsListOf780_1() throws Exception;
}
