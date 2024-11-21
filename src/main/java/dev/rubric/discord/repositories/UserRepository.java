package dev.rubric.discord.repositories;

import dev.rubric.discord.collections.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> getUserByUserId(Long userId);
    Optional<User> getUserByUsername(String username);
    Boolean existsByUserId(Long userId);
    void deleteByUserId(Long userId);
}
