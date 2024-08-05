package com.apapeasy.stockguard;

import com.apapeasy.stockguard.model.User;
import com.apapeasy.stockguard.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockguardApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockguardApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(UserService userService){
		return args -> {

			if (userService.getAllUsers().isEmpty()){
				var admin = new User();
				admin.setEmail("admin@gmail.com");
				admin.setRole("admin");
				admin.setPassword("admin123");
				admin.setUsername("admin");
				userService.registerUser(admin);

				var storemanager = new User();
				storemanager.setEmail("store@gmail.com");
				storemanager.setRole("storemanager");
				storemanager.setPassword("storemgr123");
				storemanager.setUsername("storemanager");
				userService.registerUser(storemanager);

				var warehouse = new User();
				warehouse.setEmail("warehouse@gmail.com");
				warehouse.setRole("warehousemanager");
				warehouse.setPassword("waremgr123");
				warehouse.setUsername("warehouse");
				userService.registerUser(warehouse);
			}
		};
	}
}
