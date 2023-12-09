package com.booklog.booklog.domain.user.repository;

import com.booklog.booklog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}