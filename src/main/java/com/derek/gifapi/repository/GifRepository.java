package com.derek.gifapi.repository;

import com.derek.gifapi.entitys.Gif;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class provides extra methods to interact with gifs in the database.
 * @see <a href="https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html">for more info on repositories</a>
 */
public interface GifRepository extends CrudRepository<Gif, Long> {
    List<Gif> findGifsByCollectionId(long collectionId);
    Optional<Gif> findGifByCollectionIdAndId(long collectionId, long id);
    Optional<Gif> findGifByCollectionIdAndGiphyId(long collectionId, String giphyId);
}
