package com.team555.inu.ringmybell_mainserver;


import com.team555.inu.ringmybell_mainserver.server.dao.RingMyBellMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RingmybellMainserverApplicationTests {

    @Autowired
    private RingMyBellMapper ringMyBellMapper;

    @Test
    public void test() {
        try {
            System.out.println(ringMyBellMapper.selectBusStopsListOf780_1());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
