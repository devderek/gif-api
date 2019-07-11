package com.derek.gifapi.repository;

import com.derek.gifapi.entitys.Collection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class provides extra methods to interact with collections in the database.
 * @see <a href="https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html">for more info on repositories</a>
 */
public interface CollectionRepository extends CrudRepository<Collection, String> {
    List<Collection> findCollectionsByUserId(long userId);
    Optional<Collection> findCollectionByUserIdAndId(long userId, long collectionId);
    Optional<Collection> findCollectionByUserIdAndCollectionName(long userId, String collectionName);
}
