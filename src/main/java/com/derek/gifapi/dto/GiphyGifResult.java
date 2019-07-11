package com.derek.gifapi.dto;

import lombok.Data;

/**
 * This class represents the JSON object for a single GIF from Giphy.
 * @see <a href="https://developers.giphy.com/docs/api/endpoint#get-gif-by-id">the Giphy API docs</a>
 */
@Data
public class GiphyGifResult {
    private GiphyGifDto data;
    // private ... meta;
}
