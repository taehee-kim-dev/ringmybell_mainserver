package com.team555.inu.ringmybell_mainserver.server.dao;

import com.team555.inu.ringmybell_mainserver.server.vo.Android;
import com.team555.inu.ringmybell_mainserver.server.vo.SearchedBusRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class SearchBusRoutesListDao {

    private final RingMyBellMapper ringMyBellMapper;

    public List<SearchedBusRoute> run(Android android){
        List<SearchedBusRoute> result = null;

        try {
            result = ringMyBellMapper.selectSearchedBusRoutesList(android);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
