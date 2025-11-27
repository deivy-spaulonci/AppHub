package com.br.repository;

import com.br.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp, Long> {
    // Método que o CustomUserDetailsService precisa
    Optional<UserApp> findByUsername(String username);
}
