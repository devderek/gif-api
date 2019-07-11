package com.derek.gifapi.service;

import com.derek.gifapi.entitys.Gif;
import com.derek.gifapi.exceptions.ConflictException;
import com.derek.gifapi.exceptions.NotFoundException;
import com.derek.gifapi.repository.GifRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GifService {
    private final GifRepository gifRepository;

    public Optional<Gif> getGifInCollection(long collectionId, long gifId) {
        return gifRepository.findGifByCollectionIdAndId(collectionId, gifId);
    }

    public List<Gif> getGifsInCollection(long collectionId) {
        return gifRepository.findGifsByCollectionId(collectionId);
    }

    public synchronized Gif addGifToCollection(Gif gif) throws ConflictException {
        // Check that no GIF already exists in the collection
        Optional<Gif> foundGif = gifRepository.findGifByCollectionIdAndGiphyId(gif.getCollectionId(), gif.getGiphyId());
        if (foundGif.isPresent()) {
            throw new ConflictException(String.format("Gif with giphy ID '%s' is already in the collection with ID '%s'", foundGif.get().getGiphyId(), foundGif.get().getCollectionId()));
        }

        return gifRepository.save(gif);
    }

    public synchronized void removeGifFromCollection(long collectionId, long gifId) throws NotFoundException {
        Optional<Gif> foundGif = gifRepository.findGifByCollectionIdAndId(collectionId, gifId);
        if (!foundGif.isPresent()) {
            throw new NotFoundException(String.format("No GIF with ID '%s' found in collection with ID '%s'", gifId, collectionId));
        }

        gifRepository.deleteById(gifId);
    }
}
