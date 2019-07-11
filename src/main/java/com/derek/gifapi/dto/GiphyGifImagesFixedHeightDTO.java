package com.derek.gifapi.dto;

import lombok.Data;

/**
 * This class represents the JSON object for a single Giphy GIF's images block with a fix height image reference. Note that a lot of fields are not present because we do not need them.
 * @see <a href="https://developers.giphy.com/docs/api/schema">the Giphy API schema</a>
 */
@Data
public class GiphyGifImagesFixedHeightDTO {
    private String url;
}
