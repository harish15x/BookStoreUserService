package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmailId(String email);
}
