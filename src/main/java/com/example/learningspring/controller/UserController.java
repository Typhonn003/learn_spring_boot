package com.example.learningspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learningspring.dto.CreateDepositDto;
import com.example.learningspring.dto.UserDto;
import com.example.learningspring.model.User;
import com.example.learningspring.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody final UserDto userData) {
        final User createdUser = userService.createUser(userData);

        return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> readUsers() {
        final List<User> allUsers = userService.readUsers();

        return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> retrieveUser(@PathVariable final String id) {
        final User user = userService.retrieveUser(Long.parseLong(id));

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody final UserDto userData, @PathVariable final String id) {
        final User user = userService.updateUser(userData, Long.parseLong(id));

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final String id) {
        userService.deleteUser(Long.parseLong(id));

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<User> createDeposit(@Valid @RequestBody final CreateDepositDto depositData,
            @PathVariable final String id) {
        final User user = userService.createDeposit(depositData, Long.parseLong(id));

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
