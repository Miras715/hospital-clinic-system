package com.example.final1.repository;

import com.example.final1.entity.AmalbekMirasUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmalbekMirasUserRepository extends JpaRepository<AmalbekMirasUser, Long> {

    Optional<AmalbekMirasUser> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
