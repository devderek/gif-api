package com.derek.gifapi.controller;

import com.derek.gifapi.exceptions.ExternalDependencyException;
import com.derek.gifapi.service.GifDiscoveryService;
import com.derek.gifapi.dto.GiphyGifDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class handles all of the requests sent to the Giphy "gifs" resource.
 */
@RequestMapping("/gifs")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GifDiscoveryController {
    private final GifDiscoveryService gifsService;

    public GifDiscoveryController(GifDiscoveryService gifsService) {
        this.gifsService = gifsService;
    }

    @RequestMapping(method= RequestMethod.GET)
    public List<GiphyGifDto> getGifs(@RequestParam("query") String query) throws ExternalDependencyException {
        return gifsService.getGiphyGifs(query);
    }
}
