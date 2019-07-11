package com.derek.gifapi.controller;

import com.derek.gifapi.dto.GiphyGifDto;
import com.derek.gifapi.entitys.Collection;
import com.derek.gifapi.entitys.Gif;
import com.derek.gifapi.entitys.User;
import com.derek.gifapi.exceptions.BadRequestException;
import com.derek.gifapi.exceptions.ConflictException;
import com.derek.gifapi.exceptions.ExternalDependencyException;
import com.derek.gifapi.exceptions.NotFoundException;
import com.derek.gifapi.service.CollectionService;
import com.derek.gifapi.service.GifDiscoveryService;
import com.derek.gifapi.service.GifService;
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
 * This class handles all of the requests sent to the "gifs" resource for given collection and user.
 */
@RestController
@RequestMapping("/users/{user}/collections/{collection}/gifs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class GifController {
    private final Logger logger = LoggerFactory.getLogger(GifController.class);

    private final GifService gifService;
    private final GifDiscoveryService gifDiscoveryService;
    private final CollectionService collectionService;
    private final UserService userService;

    @RequestMapping(path="/{gif}", method=RequestMethod.GET)
    public ResponseEntity<Gif> getGifInCollection(@PathVariable("user") long userId,
                                      @PathVariable("collection") long collectionId,
                                      @PathVariable("gif") long gifId) throws NotFoundException {
        logger.info("Request to get GIF with ID '{}' in collection with ID '{}' and user ID '{}'", gifId, collectionId, userId);

        // Ensure that user ID exists
        verifyUserExists(userId);
        // Ensure that the collection ID exists
        verifyCollectionExists(userId, collectionId);
        // Retrieve the gif
        Optional<Gif> gif = gifService.getGifInCollection(collectionId, gifId);
        if (!gif.isPresent()) {
            throw new NotFoundException(String.format("No GIF found with ID '%s' in collection '%s'", collectionId, gifId));
        }

        logger.info("Successfully retrieved GIF with ID '{}' in collection with ID '{}' and user ID '{}'", gifId, collectionId, userId);
        return new ResponseEntity<>(gif.get(), HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Gif>> getGifsInCollection(@PathVariable("user") long userId,
                                             @PathVariable("collection") long collectionId) throws NotFoundException {
        logger.info("Request to get all GIFs in collection with ID '{}' and user ID '{}'", collectionId, userId);

        // Ensure that user ID exists
        verifyUserExists(userId);
        // Ensure that the collection ID exists
        verifyCollectionExists(userId, collectionId);
        // Retrieve the gifs in the collection
        List<Gif> gifs = gifService.getGifsInCollection(collectionId);

        logger.info("Request to get all GIFs in collection with ID '{}' and user ID '{}'", collectionId, userId);
        return new ResponseEntity<>(gifs, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Gif> addGifToCollection(@Valid @RequestBody Gif gif,
                                                         @PathVariable("user") long userId,
                                                         @PathVariable("collection") long collectionId) throws NotFoundException, BadRequestException, ConflictException, ExternalDependencyException {
        logger.info("Request to add GIF with Giphy ID '{}' to collection with ID '{}' and user ID '{}'", gif.getGiphyId(), gif.getCollectionId(), userId);

        // Ensure that the collection ID in the path is the same as the collection ID in the request
        if (collectionId != gif.getCollectionId()) {
            throw new BadRequestException("Collection ID did not match the collection ID in the request body.");
        }
        // Ensure that the user ID in the path is the same as the user ID in the request
        Optional<GiphyGifDto> foundGiphyGif = gifDiscoveryService.getGiphyGifById(gif.getGiphyId());
        if (!foundGiphyGif.isPresent()) {
            throw new BadRequestException(String.format("Giphy has no gif with ID '%s'", gif.getGiphyId()));
        }
        // Ensure that user ID exists
        verifyUserExists(userId);
        // Ensure that the collection ID exists
        verifyCollectionExists(userId, collectionId);
        // Create the gif
        Gif created = gifService.addGifToCollection(gif);

        logger.info("Successfully add GIF with Giphy ID '{}' to collection with ID '{}' and user with ID '{}'", gif.getGiphyId(), gif.getCollectionId(), userId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @RequestMapping(path="/{gif}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> removeGifFromCollection(@PathVariable("user") long userId,
                                                       @PathVariable("collection") long collectionId,
                                                       @PathVariable("gif") long gifId) throws NotFoundException {
        logger.info("Request to delete GIF with ID '{}' from collection with ID '{}'", gifId, collectionId);

        // Ensure that user ID exists
        verifyUserExists(userId);
        // Ensure that the collection ID exists
        verifyCollectionExists(userId, collectionId);
        // Remove the gif from the collection
        gifService.removeGifFromCollection(collectionId, gifId);

        logger.info("Successfully deleted GIF with ID '{}' from collection with ID '{}'", gifId, collectionId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    /**
     * Private helper method to ensure a user exists before doing gif actions.
     */
    private User verifyUserExists(long userId) throws NotFoundException {
        // See if the user exists first
        Optional<User> user = userService.getUserById(userId);
        if (!user.isPresent()) {
            throw new NotFoundException(String.format("No user found with ID '%s'", userId));
        }

        return user.get();
    }

    /**
     * Private helper method to ensure a collection exists before doing gif actions.
     */
    private Collection verifyCollectionExists(long userId, long collectionId) throws NotFoundException {
        // See if the user exists first
        Optional<Collection> collection = collectionService.getUserCollection(userId, collectionId);
        if (!collection.isPresent()) {
            throw new NotFoundException(String.format("No collection found with ID '%s'", collectionId));
        }

        return collection.get();
    }
}
