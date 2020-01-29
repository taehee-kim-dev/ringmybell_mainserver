package com.team555.inu.ringmybell_mainserver.server.service;

import com.team555.inu.ringmybell_mainserver.server.dao.MySqlDao;
import com.team555.inu.ringmybell_mainserver.server.vo.BusStop;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfirmBusService {

    private final MySqlDao mySqlDao;

    public ConfirmBusService(MySqlDao mySqlDao) {
        this.mySqlDao = mySqlDao;
    }

    public List<BusStop> run(String routeNum){

        List<BusStop> result = null;

        if(routeNum.equals("780-1")){
            try {
                 result = mySqlDao.selectBusStopsListOf780_1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
