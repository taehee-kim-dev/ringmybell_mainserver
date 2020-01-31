package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UpdateReservationDao {

    private final RingMyBellMapper ringMyBellMapper;

    public int run(Android android){
        int result = -100;

        try {
            result = ringMyBellMapper.updateReservation(android);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
