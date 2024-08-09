package cat.itacademy.s05.t01.S05T01.chips.data;

import cat.itacademy.s05.t01.S05T01.security.data.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * This is a magic interface, which is converted
 * to a class during compilation.
 * <p>
 * Note that this introduces coupling between Chips and the way they are stored!
 * In more loosely coupled components, you would add an intermediary abstraction
 * like an abstract repository or a DAO!
 */
public interface SpringChipsRepository extends ReactiveCrudRepository<Chips, Long> {
    Mono<Chips> findByUser(User user);
}