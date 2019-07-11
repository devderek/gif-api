package com.derek.gifapi.controller;

import com.derek.gifapi.entitys.Collection;
import com.derek.gifapi.entitys.User;
import com.derek.gifapi.exceptions.BadRequestException;
import com.derek.gifapi.exceptions.ConflictException;
import com.derek.gifapi.service.CollectionService;
import com.derek.gifapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * This class handles all of the requests sent to the "collections" resource for a user.
 */
@RestController
@RequestMapping("/users/{user}/collections")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CollectionController {
    private final Logger logger = LoggerFactory.getLogger(CollectionController.class);

    private final CollectionService collectionService;
    private final UserService userService;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Collection>> getCollections(@PathVariable("user") long userId) throws BadRequestException {
        logger.info("Request to get all collections for user with ID '{}'", userId);

        // Ensure that user ID exists
        verifyUserExists(userId);
        // Retrieve the collections
        List<Collection> collections = collectionService.getUserCollections(userId);

        logger.info("Successfully retrieved collections for user with ID '{}'", userId);
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Collection> createCollection(@Valid @RequestBody Collection collection, @PathVariable("user") long userId) throws BadRequestException, ConflictException {
        logger.info("Request to create collection '{}' for user with ID '{}'", collection.getCollectionName(), collection.getUserId());

        // Ensure that the user ID in the path is the same as the user ID in the request
        if (userId != collection.getUserId()) {
            throw new BadRequestException("User ID did not match the user ID in the request body.");
        }
        // Ensure that user ID exists
        verifyUserExists(userId);
        // Start creating the collection
        Collection createdCollection = collectionService.createCollection(collection);

        logger.info("Successfully created collection '{}' for user with ID '{}'", collection.getCollectionName(), collection.getUserId());
        return new ResponseEntity<>(createdCollection, HttpStatus.CREATED);
    }

    /**
     * Private helper method to ensure a user exists before doing collection actions.
     */
    private void verifyUserExists(long userId) throws BadRequestException {
        // See if the user exists first
        Optional<User> user = userService.getUserById(userId);
        if (!user.isPresent()) {
            throw new BadRequestException(String.format("No user found with ID '%s'", userId));
        }
    }
}
