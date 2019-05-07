package com.ipv.service.imple;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
    @Scheduled(fixedRate = 2000)
    public void reportCurrentTime() {
    	System.out.println("hello it's me");
    }
}
