package com.epam.security.config;

import com.epam.security.service.BruteForceProtectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final BruteForceProtectionService bruteForceProtectionService;

    public AuthenticationSuccessListener(BruteForceProtectionService bruteForceProtectionService) {
        this.bruteForceProtectionService = bruteForceProtectionService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        log.info("********* login successful for user {} ", username);
        bruteForceProtectionService.resetBruteForceCounter(username);
    }
}
