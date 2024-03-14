package com.booklog.booklog.domain.user.repository;

import com.booklog.booklog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);
    boolean existsByEmail(String email);

    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
}