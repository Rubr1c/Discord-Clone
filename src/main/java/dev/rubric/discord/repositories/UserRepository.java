package dev.rubric.discord.repositories;

import dev.rubric.discord.collections.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> getUserByUserId(Integer userId);
    Optional<User> getUserByUsername(String username);
    Boolean existsByUserId(Integer userId);
    void deleteByUserId(Integer userId);
}
