package com.example.rtapservice.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PresenceCacheService {
    // Cache maps Session ID to a Set of User IDs
    private final Cache<Long, Set<Long>> activeUsersCache = Caffeine.newBuilder()
            .expireAfterAccess(2, TimeUnit.HOURS)
            .build();

    public void markUserPresent(Long sessionId, Long userId) {
        Set<Long> users = activeUsersCache.get(sessionId, k -> java.util.concurrent.ConcurrentHashMap.newKeySet());
        users.add(userId);
        activeUsersCache.put(sessionId, users);
    }

    public void markUserAbsent(Long sessionId, Long userId) {
        Set<Long> users = activeUsersCache.getIfPresent(sessionId);
        if (users != null) {
            users.remove(userId);
        }
    }

    public Set<Long> getActiveUsers(Long sessionId) {
        return activeUsersCache.getIfPresent(sessionId);
    }
}
