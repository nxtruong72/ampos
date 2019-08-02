package com.ampos.restaurant;

import com.ampos.restaurant.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantApplication implements CommandLineRunner {
	@Autowired
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}
}
