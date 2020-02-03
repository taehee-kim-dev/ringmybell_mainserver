package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.RasberryPi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CheckBusLocationDao {

    private final RingMyBellMapper ringMyBellMapper;

    // 버스의 현재 위치 반환
    // 매개변수는 위도와 경도
    public String run(RasberryPi rasberryPi){
        String result = null;

        try {
            if(rasberryPi.getRouteNum().equals("780-1")){
                result = ringMyBellMapper.checkBusLocationOf780_1(rasberryPi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}