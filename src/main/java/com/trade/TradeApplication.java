package com.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.trade")
public class TradeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeApplication.class, args);
	}

}
