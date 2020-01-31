package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DeleteReservationDao {

    private final RingMyBellMapper ringMyBellMapper;

    public int run(Android android){
        int result = -100;

        try {
            result = ringMyBellMapper.deleteReservation(android);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
