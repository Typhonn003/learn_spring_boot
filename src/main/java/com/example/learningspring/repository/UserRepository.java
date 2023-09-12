package com.example.learningspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.learningspring.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByCpf(final String cpf);

    boolean existsUserByEmail(final String email);
}
