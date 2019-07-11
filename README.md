# Gif API

## Purpose

The purpose of this project is to expose RESTful APIs that the Gif UI project utilizes to function.

## Architecture

The app has four major functions:
- Retrieve GIFs from Giphy
- Register/get users
- Create/get user GIF collections
- Add/remove GIFs to/from collections

Every user of the system can have zero to infinitely many collections. Every collection can contain zero to infinitely many GIFs. Every time a new user is made they are given a collection automatically with the name `default`. This is where all uncategorized GIFs will live.

All data will be stored in an internal H2 database that is packaged with the application. This could be easily swapped for an external database assuming it was a real application. An external database would also allow for infinite API replicas to run to handle any amount of user load. 

## API

This section outlines the HTTP endpoints that exist for this API. Objects references in the last two columns are outlined in more detail in the next section. Objects with `[]` at the end indicate that a collection of that object is used. All APIs support JSON only.

| Method | Endpoint | Description | API Object Expected | API Object Returned |
| --- | ---:| --- | ---:|:--- |
| GET | /api/v1/gifs | Retrieves gifs from Giphy. Requires query parameter "query" to do the search. Returns an ordered list of Giphy GIFs | | Giphy GIF[] |
| GET | /api/v1/users | Retrieves registered users in the system | | User[] |
| POST | /api/v1/users | Creates a user in the system | User | |
| GET | /api/v1/users/{userId}/collections | Retrieves all collections of the user with ID "userId" | | Collection[] |
| POST | /api/v1/users/{userId}/collections | Creates a collection for the user with ID "userId" | Collection | |
| GET | /api/v1/users/{userId}/collections/{collectionId}/gifs | Retrieves all gifs in collection with ID "collectionId" | | Gif[] |
| POST | /api/v1/users/{userId}/collections/{collectionId}/gifs | Adds a gif to the collection with ID "collectionId" | Gif | |

## API Objects

These are the JSON representations of each object you'll encounter using this API. Not all fields are needed in all requests. See `(notes)` in each object for more details.

**Giphy GIF Object**
```
{
    "id": "TztOD2c0znrtm",
    "title": "elmo GIF",
    "images": {
        "fixed_height": {
            "url": "https://media3.giphy.com/media/TztOD2c0znrtm/200.gif?cid=b3d0ffe45d23e512466a785777d6b2d4&rid=200.gif"
        }
    }
}
```

**User Object**
```
{
    "id": 1, (only used when retrieving a user or a list of users)
    "username": "user",
    "password": "password" (only used on create user requests)
}
```

**Collection Object**
```
{
    "id": 2, (only used when retrieving a collection or a list of collections)
    "collectionName": "default"
}
```

**GIF Object**
```
{
    "id": 3, (only used when retrieving a gif or a list of gifs)
    "giphyId": "TztOD2c0znrtm",
    "giphyUrl": "https://media3.giphy.com/media/TztOD2c0znrtm/200.gif?cid=b3d0ffe45d23e512466a785777d6b2d4&rid=200.gif"
}
```

## Running the API

The following are required to run this API:
- In order to build this project you will need a Java 1.8 JDK installed
- In order to build this project you will need access to the internet (to download project dependencies)
- In order to run this project you will need a Java 1.8 JDK installed or a Java 1.8 JRE installed
- You will need to obtain an API key from Giphy by [making a developer account here](https://developers.giphy.com/)
- You will need to set an environment property. The key is `giphy.apiKey` and the value is equal to the API key generated from the previous step. This property will be sent to the Giphy API anytime the app needs to call Giphy.
- You will need to set an environment property. The key is `db.salt` and the value is equal to a random set of lowercase letters, uppercase letters, and numbers. The length of the string does not matter. This property will be used to encrypt passwords sent to the API. This ensures all passwords stored in the database are secure, even if an attacker were to obtain the raw database data.

In order to start the API run the following command from the project's home directory:

On Unix: `./gradlew clean build bootRun`

On Windows: `gradlew.bat clean build bootRun`

Running the appropriate command will start the API server. By default it will start on port 8080.

To verify the API started you should see a log similar to the following at the end of the initial stream of logs:
```
Started GifApiApplication in 7.048 seconds (JVM running for 7.344)
```