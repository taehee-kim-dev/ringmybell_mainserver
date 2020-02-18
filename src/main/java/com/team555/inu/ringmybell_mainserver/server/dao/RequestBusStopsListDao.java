package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.BusStop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RequestBusStopsListDao {

    private final RingMyBellMapper ringMyBellMapper;

    public List<BusStop> run(Android android){
        List<BusStop> result = null;

        if(android.getRouteNum().equals("780-1")){
            try {
                result = ringMyBellMapper.selectBusStopsListOf780_1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
