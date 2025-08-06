package com.pro.list_tick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ListTickApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListTickApplication.class, args);
	}
}
