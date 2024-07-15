package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BasicApplication {
	public static void main(String[] args) {
		System.out.println("시작");
		SpringApplication.run(BasicApplication.class, args);
	}

}
