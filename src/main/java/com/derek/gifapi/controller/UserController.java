package com.derek.gifapi.controller;

import com.derek.gifapi.entitys.Collection;
import com.derek.gifapi.entitys.User;
import com.derek.gifapi.exceptions.ConflictException;
import com.derek.gifapi.exceptions.UnrecoverableException;
import com.derek.gifapi.service.CollectionService;
import com.derek.gifapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * This class handles all of the requests sent to the "users" resource.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final CollectionService collectionService;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        logger.info("Request to get all users");

        // Retrieve all users
        List<User> users = userService.getUsers();

        logger.info("Successfully retrieved users");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws UnrecoverableException, ConflictException {
        logger.info("Request to create user with name '{}'", user.getUsername());

        // Create the user
        User createdUser = userService.createUser(user);
        // Create the default collection for the user
        try {
            Collection defaultCollection = new Collection();
            defaultCollection.setUserId(createdUser.getId());
            defaultCollection.setCollectionName("default");
            collectionService.createCollection(defaultCollection);
        } catch (Exception e) {
            // We need to roll back if there was a failure making the collection
            userService.deleteUser(createdUser);
            throw new UnrecoverableException("Failed to create default user collection.", e);
        }

        logger.info("Successfully created user with name '{}'", user.getUsername());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
