package com.derek.gifapi.service;

import com.derek.gifapi.entitys.User;
import com.derek.gifapi.exceptions.ConflictException;
import com.derek.gifapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> getUsers() {
        // Retrieve all users from the database
        ArrayList<User> users = new ArrayList<>();
        userRepository.findAll().forEach(r -> {
            // Clear out the encrypted password before sending it to the user
            r.setPassword(null);
            users.add(r);
        });
        return users;
    }

    public synchronized User createUser(User user) throws ConflictException {
        // See if there is an existing user
        Optional<User> foundUser = getUserByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            throw new ConflictException(String.format("User with name '%s' already exists", user.getUsername()));
        }

        // Encrypt the password for storage
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Commit user to database
        User createdUser = userRepository.save(user);

        // Create out details we don't need the user to see
        createdUser.setPassword("");

        // Return the created user
        return createdUser;
    }

    public synchronized void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }
}
