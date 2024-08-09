package cat.itacademy.s05.t01.S05T01.security.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SpringUserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByUsername(String username);
}