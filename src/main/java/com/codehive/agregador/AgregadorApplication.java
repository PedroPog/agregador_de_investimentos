package com.codehive.agregador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AgregadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgregadorApplication.class, args);
	}

}
