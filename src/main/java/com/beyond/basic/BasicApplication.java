package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 해당 어노테이션을 통해 ComponentScanning을 수행한다.
// ComponentScan이 들어가 있다
@SpringBootApplication
public class BasicApplication {
	public static void main(String[] args) {
		System.out.println("시작");
		SpringApplication.run(BasicApplication.class, args);
	}
}
