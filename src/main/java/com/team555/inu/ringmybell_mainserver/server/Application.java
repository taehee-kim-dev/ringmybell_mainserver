package com.team555.inu.ringmybell_mainserver.server;

import com.team555.inu.ringmybell_mainserver.server.networking.android.ServerForAndroid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Application implements ApplicationRunner {

    private ServerForAndroid serverForAndroid;

    public Application(ServerForAndroid serverForAndroid) {
        this.serverForAndroid = serverForAndroid;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        log.info("Application 시작!!");

        serverForAndroid.run();
    }
}
