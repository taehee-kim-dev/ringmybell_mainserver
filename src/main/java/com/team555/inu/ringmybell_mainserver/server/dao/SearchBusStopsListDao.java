package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.BusStop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchBusStopsListDao {

    private final RingMyBellMapper ringMyBellMapper;

    public SearchBusStopsListDao(RingMyBellMapper ringMyBellMapper) {
        this.ringMyBellMapper = ringMyBellMapper;
    }

    public List<BusStop> run(String routeNum){
        List<BusStop> result = null;

        if(routeNum.equals("780-1")){
            try {
                result = ringMyBellMapper.selectBusStopsListOf780_1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
