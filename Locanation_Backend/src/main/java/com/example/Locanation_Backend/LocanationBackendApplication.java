package com.example.Locanation_Backend;

import com.example.Locanation_Backend.model.User;
import com.example.Locanation_Backend.repository.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class LocanationBackendApplication {


	public static void main(String[] args) {
		System.setProperty("ucanaccess.skipFunctions", "true");
		ApplicationContext context = SpringApplication.run(LocanationBackendApplication.class, args);
		UserRepo repo = context.getBean(UserRepo.class);
		Optional<User> user = repo.findById(1);
		System.out.println(user);
	}

}
