package com.example.rtapservice.repository;

import com.example.rtapservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // We will need this specific method later when we implement JWT Security
    Optional<User> findByUsername(String username);
}
