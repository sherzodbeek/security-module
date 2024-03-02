package com.epam.security;

import com.epam.security.entity.User;
import com.epam.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecurityApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		User user = User.builder()
				.username("username")
				.password(passwordEncoder.encode("password"))
				.build();
		userRepository.save(user);
	}
}
