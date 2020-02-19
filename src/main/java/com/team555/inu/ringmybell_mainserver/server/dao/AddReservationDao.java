package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AddReservationDao {

    private final RingMyBellMapper ringMyBellMapper;

    public int run(Android android){
        int result = -100;

        try {
            result = ringMyBellMapper.countReservation(android);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(result > 0){
            try {
                result = ringMyBellMapper.updateReservation(android);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(result == 0){
            try {
                result = ringMyBellMapper.addReservation(android);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
