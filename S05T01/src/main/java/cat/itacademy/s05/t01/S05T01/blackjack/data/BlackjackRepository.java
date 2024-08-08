package cat.itacademy.s05.t01.S05T01.blackjack.data;

import cat.itacademy.s05.t01.S05T01.security.data.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface BlackjackRepository extends ReactiveCrudRepository<Blackjack, Long> {
    Mono<Blackjack> findById(Long Id);

    //get's the last game of specified user
    Optional<Blackjack> findTopByUserAndGameDoneOrderByIdDesc(User user, Boolean gameDone);
}


