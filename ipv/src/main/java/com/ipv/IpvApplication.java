package com.ipv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/*
 * The entrance of the application
 * */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class IpvApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpvApplication.class, args);
	}

}
