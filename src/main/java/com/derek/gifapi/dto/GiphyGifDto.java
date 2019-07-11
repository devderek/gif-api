package com.derek.gifapi.dto;

import lombok.Data;

/**
 * This class represents the JSON object for a single Giphy GIF. Note that a lot of fields are not present because we do not need them.
 * @see <a href="https://developers.giphy.com/docs/api/schema">the Giphy API schema</a>
 */
@Data
public class GiphyGifDto {
    private String id;
    private String title;
    private GiphyGifImagesDto images;
}
