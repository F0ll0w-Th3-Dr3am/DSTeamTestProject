package com.example.h2_DiSpProject;

import lombok.extern.java.Log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@Log
public class H2DiSpProjectApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		log.info("Rest API server started");
		SpringApplication.run(H2DiSpProjectApplication.class, args);
	}
}
