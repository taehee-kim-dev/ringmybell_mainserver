package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.BusStop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class RequestBusStopsListDao {

    private final RingMyBellMapper ringMyBellMapper;

    public List<BusStop> run(Android android){
        List<BusStop> result = null;

        switch (android.getRouteNum()){
            case "780-1":
                try {
                    result = ringMyBellMapper.selectBusStopsListOf780_1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "908":
                try {
                    result = ringMyBellMapper.selectBusStopsListOf908();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                log.error("RequestBusStopsListDao에서 요청 routeNum의 값이 switch문의 default로 빠짐.");
        }

        return result;
    }
}
