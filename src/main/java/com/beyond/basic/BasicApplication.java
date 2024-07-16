package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// 해당 어노테이션을 통해 ComponentScanning을 수행한다.
// ComponentScan이 들어가 있다
@SpringBootApplication
// 주소 web서블릿 기반의 구성요소를 스캔하고, 자동으로 등록하는 기능
// 예를 들어)@WebServlet, @WebFilter, @WebListener등
@ServletComponentScan
public class BasicApplication {
	public static void main(String[] args) {
		System.out.println("시작");
		SpringApplication.run(BasicApplication.class, args);
	}
}
