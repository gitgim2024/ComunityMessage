package com.tech.whale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// [ 메인 엔트리 ]
@SpringBootApplication(scanBasePackages = "com.tech.*")
public class WhaleApplication {
	public static void main(String[] args) {
		SpringApplication.run(WhaleApplication.class, args);
	}
}
