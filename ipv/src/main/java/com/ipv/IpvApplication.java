package com.ipv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 * The entrance of the application
 * */
@EnableScheduling
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class IpvApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpvApplication.class, args);
	}

}
