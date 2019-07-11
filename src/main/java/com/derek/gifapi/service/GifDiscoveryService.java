package com.derek.gifapi.service;

import com.derek.gifapi.client.GiphyClient;
import com.derek.gifapi.dto.GiphyGifDto;
import com.derek.gifapi.dto.GiphyGifResult;
import com.derek.gifapi.dto.GiphyGifSearchResult;
import com.derek.gifapi.exceptions.BadRequestException;
import com.derek.gifapi.exceptions.ExternalDependencyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GifDiscoveryService {
    private final GiphyClient giphyClient;

    public Optional<GiphyGifDto> getGiphyGifById(String giphyGifId) throws BadRequestException {
        GiphyGifResult result = null;
        try {
            result = giphyClient.getGiphyGifById(giphyGifId);
            if (null == result) {
                throw new ExternalDependencyException("Giphy lookup failed for an unknown reason");
            }
            return Optional.of(result.getData());
        } catch (Exception e) {
            throw new BadRequestException("Failed to call Giphy service. It is likely that the passed in Giphy ID was incorrect.", e);
        }
    }

    public List<GiphyGifDto> getGiphyGifs(String query) throws ExternalDependencyException {
        GiphyGifSearchResult result = giphyClient.getGiphyGifs(query);
        if (null == result) {
            throw new ExternalDependencyException("Giphy search failed for an unknown reason");
        }
        return result.getData();
    }
}
