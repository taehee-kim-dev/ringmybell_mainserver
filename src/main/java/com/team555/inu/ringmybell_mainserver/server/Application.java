package com.team555.inu.ringmybell_mainserver.server;

import com.team555.inu.ringmybell_mainserver.server.networking.android.ServerForAndroid;
import com.team555.inu.ringmybell_mainserver.server.networking.rasberrypi.ServerForRasberryPi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

@Slf4j
@AllArgsConstructor
@Component
public class Application implements ApplicationRunner {

    private ServerForAndroid serverForAndroid;
    private ServerForRasberryPi serverForRasberryPi;

    @Override
    public void run(ApplicationArguments args) throws Exception{

        System.setProperty("file.encoding","UTF-8");

        Field charset = null;
        try {
            charset = Charset.class.getDeclaredField("defaultCharset");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        charset.setAccessible(true);

        try {
            charset.set(null,null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        log.info("Application 시작!!");

        serverForAndroid.run();
        serverForRasberryPi.run();
    }
}
