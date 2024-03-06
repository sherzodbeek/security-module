package com.epam.security.service;

import com.epam.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BruteForceProtectionService bruteForceProtectionService;

    public MyUserDetailsService(UserRepository userRepository, BruteForceProtectionService bruteForceProtectionService) {
        this.userRepository = userRepository;
        this.bruteForceProtectionService = bruteForceProtectionService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (bruteForceProtectionService.isBruteForceAttack(username)) {
            throw new RuntimeException("Blocked");
        }

        return userRepository.
                findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
