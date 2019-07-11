-- Drop any existing tables
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS COLLECTIONS;
DROP TABLE IF EXISTS COLLECTIONS_GIFS;
DROP TABLE IF EXISTS GIFS;

-- Where all user information will be stored
CREATE TABLE USERS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL
);

-- Where all collections of all users will be stored
CREATE TABLE COLLECTIONS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    collection_name VARCHAR(64) NOT NULL
);

-- This is an intersection table since many collections can contain many gifs (many-to-many relationship)
CREATE TABLE GIFS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    collection_id INT NOT NULL,
    giphy_id VARCHAR(64) NOT NULL,
    giphy_url VARCHAR(256) NOT NULL
);

--CREATE TABLE GIFS (
--    id INT AUTO_INCREMENT PRIMARY KEY,
--    giphy_id VARCHAR(64) NOT NULL
--);

-- Begin setting up foreign key relationships
-- Users will have collections
ALTER TABLE COLLECTIONS ADD FOREIGN KEY (user_id) REFERENCES USERS(id);

ALTER TABLE GIFS ADD FOREIGN KEY (collection_id) REFERENCES COLLECTIONS(id);
-- Collections will have many gifs
--ALTER TABLE COLLECTIONS_GIFS ADD FOREIGN KEY (collection_id) REFERENCES COLLECTIONS(id);
-- Gifs will be in many collections
--ALTER TABLE COLLECTIONS_GIFS ADD FOREIGN KEY (gif_id) REFERENCES GIFS(id);