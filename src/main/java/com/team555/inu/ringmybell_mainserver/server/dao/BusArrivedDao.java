package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.BusArriveInform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BusArrivedDao {

    private final RingMyBellMapper ringMyBellMapper;

    public int run(BusArriveInform busArriveInform){
        int result = -100;

        try {
            result = ringMyBellMapper.arriveAtTheBusStop(busArriveInform);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
