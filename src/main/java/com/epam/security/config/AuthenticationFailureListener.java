package com.epam.security.config;

import com.epam.security.repository.UserRepository;
import com.epam.security.service.BruteForceProtectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final BruteForceProtectionService bruteForceProtectionService;

    private final UserRepository userRepository;

    public AuthenticationFailureListener(BruteForceProtectionService bruteForceProtectionService, UserRepository userRepository) {
        this.bruteForceProtectionService = bruteForceProtectionService;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        if (userRepository.findByUsername(username).isPresent()) {
            log.info("********* login failed for user {} ", username);
            bruteForceProtectionService.registerLoginFailure(username);
        }
    }
}
