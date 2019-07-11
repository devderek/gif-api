package com.derek.gifapi;

import com.derek.gifapi.controller.UserController;
import com.derek.gifapi.entitys.Collection;
import com.derek.gifapi.entitys.Gif;
import com.derek.gifapi.entitys.User;
import com.derek.gifapi.service.CollectionService;
import com.derek.gifapi.service.GifService;
import com.derek.gifapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class GifApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(GifApiApplication.class, args);
	}

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;
	private final CollectionService collectionService;
	private final GifService gifService;

	@PostConstruct
	public void postConstruct() throws Exception {
		// Create an example user
		User user = new User();
		user.setUsername("derek");
		user.setPassword("password");
		logger.info("DATA INJECTION: Creating user '{}'", user);
		userService.createUser(user);

		// Create an example collection
		Collection collection = new Collection();
		collection.setUserId(1L);
		collection.setCollectionName("default");
		logger.info("DATA INJECTION: Creating collection for user {}", collection);
		collectionService.createCollection(collection);

		// Create an example gif
		Gif gif = new Gif();
		gif.setCollectionId(2L);
		gif.setGiphyId("TztOD2c0znrtm");
		gif.setGiphyUrl("https://giphy.com/embed/TztOD2c0znrtm");
		gifService.addGifToCollection(gif);
	}
}
