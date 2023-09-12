package com.example.learningspring.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.learningspring.dto.CreateDepositDto;
import com.example.learningspring.dto.UserDto;
import com.example.learningspring.exception.AppException;
import com.example.learningspring.model.User;
import com.example.learningspring.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void checkEmailAndCpf(final UserDto userData) {
        if (userRepository.existsUserByEmail(userData.getEmail())) {
            throw new AppException("Email already in use", HttpStatus.CONFLICT);
        }

        if (userRepository.existsUserByCpf(userData.getCpf())) {
            throw new AppException("CPF already in use", HttpStatus.CONFLICT);
        }
    }

    public User createUser(final UserDto userData) {
        checkEmailAndCpf(userData);

        final User newUser = new User(userData.getName(), userData.getCpf(), userData.getEmail(),
                userData.getPassword(),
                userData.getType());

        return userRepository.save(newUser);
    }

    public List<User> readUsers() {
        return userRepository.findAll();
    }

    public User retrieveUser(final long id) {
        final User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        return foundUser;
    }

    public User updateUser(final UserDto userData, final long id) {
        checkEmailAndCpf(userData);

        final User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        foundUser.setName(userData.getName());
        foundUser.setEmail(userData.getEmail());
        foundUser.setCpf(userData.getCpf());
        foundUser.setPassword(userData.getPassword());
        foundUser.setType(userData.getType());

        return userRepository.save(foundUser);
    }

    public void deleteUser(final long id) {
        final User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        userRepository.delete(foundUser);
    }

    public User createDeposit(final CreateDepositDto depositData, final long id) {
        final User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        final float currentBalance = foundUser.getBalance();

        foundUser.setBalance(currentBalance + depositData.getValue());

        return userRepository.save(foundUser);
    }
}
