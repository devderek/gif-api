package com.derek.gifapi.dto;

import lombok.Data;

import java.util.List;

/**
 * This class represents the JSON object for a search result from Giphy.
 * @see <a href="https://developers.giphy.com/docs/api/endpoint#search">the Giphy API documentation</a>
 */
@Data
public class GiphyGifSearchResult {
    private List<GiphyGifDto> data;
    // private ... pagination;
    // private ... meta;
}
