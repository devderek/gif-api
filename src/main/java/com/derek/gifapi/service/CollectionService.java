package com.derek.gifapi.service;

import com.derek.gifapi.entitys.Collection;
import com.derek.gifapi.exceptions.ConflictException;
import com.derek.gifapi.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;

    public Optional<Collection> getUserCollection(long userId, long collectionId) {
        return collectionRepository.findCollectionByUserIdAndId(userId, collectionId);
    }

    public List<Collection> getUserCollections(long userId) {
        // Get the collections
        List<Collection> collections = collectionRepository.findCollectionsByUserId(userId);
        // Remove the user IDs as the user doesn't need to see that info
        collections.forEach(collection -> collection.setUserId(null));
        return collections;
    }

    public synchronized Collection createCollection(Collection collection) throws ConflictException {
        // Verify no collection exists by this ID already
        Optional<Collection> foundCollection = collectionRepository.findCollectionByUserIdAndCollectionName(collection.getUserId(), collection.getCollectionName());
        if (foundCollection.isPresent()) {
            throw new ConflictException(String.format("Collection with name '%s' already exists for user with ID '%s'", collection.getCollectionName(), collection.getUserId()));
        }
        // Save the new collection
        return collectionRepository.save(collection);
    }
}
