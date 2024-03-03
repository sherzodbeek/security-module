package com.epam.security;

import com.epam.security.entity.User;
import com.epam.security.entity.UserAuthority;
import com.epam.security.repository.UserAuthorityRepository;
import com.epam.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class SecurityApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAuthorityRepository userAuthorityRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}


	@Override
	public void run(String... args) {
		UserAuthority viewInfo = UserAuthority.builder()
				.name("VIEW_INFO")
				.build();
		UserAuthority viewAdmin = UserAuthority.builder()
				.name("VIEW_ADMIN")
				.build();
		UserAuthority viewInfoAuthority = userAuthorityRepository.save(viewInfo);
		UserAuthority viewAdminAuthority = userAuthorityRepository.save(viewAdmin);

		User user = User.builder()
				.username("username")
				.password(passwordEncoder.encode("password"))
				.authorities(List.of(viewInfoAuthority))
				.build();
		User admin = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("password"))
				.authorities(List.of(viewAdminAuthority))
				.build();
		User superAdmin = User.builder()
				.username("superAdmin")
				.password(passwordEncoder.encode("password"))
				.authorities(List.of(viewAdminAuthority, viewInfoAuthority))
				.build();
		userRepository.saveAll(List.of(user, admin, superAdmin));
	}
}
