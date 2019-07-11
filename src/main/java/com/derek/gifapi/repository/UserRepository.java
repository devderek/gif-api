package com.derek.gifapi.repository;

import com.derek.gifapi.entitys.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * This class provides extra methods to interact with users in the database.
 * @see <a href="https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html">for more info on repositories</a>
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String name);
}
