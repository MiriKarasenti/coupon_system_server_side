package com.jb.couponSystem;

import com.jb.couponSystem.data.rest.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClientSessionCleanerTask implements Runnable {

    private static final int HALF_AN_HOUR = 1800000;
    private static final int SLEEP_TIME = 30000;
    private Map<String, ClientSession> tokensMap;
    private boolean running;

    @Autowired
    public ClientSessionCleanerTask(@Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.tokensMap = tokensMap;
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            tokensMap.entrySet().removeIf(current -> System.currentTimeMillis() - current.getValue().getLastAccessedMillis() > HALF_AN_HOUR);
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
