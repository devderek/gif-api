package com.derek.gifapi.client;

import com.derek.gifapi.dto.GiphyGifResult;
import com.derek.gifapi.dto.GiphyGifSearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * This class provides methods to call the Giphy API. The API key for Giphy is included via the Spring property "giphy.apiKey".
 */
@Component
public class GiphyClient {
    private RestTemplate restTemplate;

    @Value("${giphy.apiKey}")
    private String giphyApiKey;

    public GiphyClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Looks like a GIF on Giphy by a Giphy image ID.
     * @param giphyId The Giphy image ID to look up.
     * @return A Giphy result object
     */
    public GiphyGifResult getGiphyGifById(String giphyId) {
        String url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.giphy.com")
                .pathSegment("v1/gifs/", giphyId)
                .queryParam("api_key", giphyApiKey)
                .build()
                .toUriString();
        return restTemplate.getForObject(url, GiphyGifResult.class);
    }

    /**
     * Retrieves a list of GIFs from Giphy based on the query provided.
     * @param query The query used to search for related images
     * @return A Giphy search result object
     */
    public GiphyGifSearchResult getGiphyGifs(String query) {
        String url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.giphy.com")
                .path("v1/gifs/search")
                .queryParam("api_key", giphyApiKey)
                .queryParam("q", query)
                .queryParam("rating", "g")
                .build()
                .toUriString();
        return restTemplate.getForObject(url, GiphyGifSearchResult.class);
    }
}
