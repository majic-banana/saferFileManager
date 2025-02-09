package com.project.saferfilemanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.project.saferfilemanager.mapper")
public class SaferFileManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaferFileManagerApplication.class, args);
	}

}
