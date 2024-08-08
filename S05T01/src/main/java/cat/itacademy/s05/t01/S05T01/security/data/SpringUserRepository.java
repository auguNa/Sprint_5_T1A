package cat.itacademy.s05.t01.S05T01.security.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.Optional;

public interface SpringUserRepository extends ReactiveMongoRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

