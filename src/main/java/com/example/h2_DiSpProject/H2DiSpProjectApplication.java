package com.example.h2_DiSpProject;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@AllArgsConstructor
@Log
public class H2DiSpProjectApplication extends SpringBootServletInitializer /*implements ApplicationRunner*/ {
	public static void main(String[] args) {
		log.info("Rest API server started");
		SpringApplication.run(H2DiSpProjectApplication.class, args);
	}
}
