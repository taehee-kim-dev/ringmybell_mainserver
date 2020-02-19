package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AddReservationDao {

    private final RingMyBellMapper ringMyBellMapper;

    public int run(Android android){
        int result = -100;

        try {
            result = ringMyBellMapper.countReservation(android);
            log.info("현재 해당 안드로이드 식별자로 예약되어있는 데이터의 갯수 : " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(result > 0){
            try {
                result = ringMyBellMapper.updateReservation(android);
                log.info("기존 예약되어있는 데이터가 존재하여 updateReservation 실행. result = " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(result == 0){
            try {
                result = ringMyBellMapper.addReservation(android);
                log.info("기존 예약되어있는 데이터가 없어 addReservation 실행. result = " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
