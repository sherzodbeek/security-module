package com.epam.security.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class BruteForceProtectionService {

    public static final int MAX_ATTEMPT = 3;

    private LoadingCache<String, Integer> attemptsCache;

    public BruteForceProtectionService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build(new CacheLoader<>() {
            @Override
            public Integer load(final String username) {
                return 0;
            }
        });
    }

    public void registerLoginFailure(String username) {
        int attempts;
        try {
            attempts = attemptsCache.get(username);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(username, attempts);
    }

    public void resetBruteForceCounter(String username) {
        attemptsCache.put(username, 0);
    }

    public boolean isBruteForceAttack(String username) {
        try {
            return attemptsCache.get(username) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }

    public List<String> getBlockedUsers() {
        return attemptsCache.asMap().keySet()
                .stream()
                .filter(this::isBruteForceAttack).toList();
    }
}
