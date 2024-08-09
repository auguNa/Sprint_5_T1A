package cat.itacademy.s05.t01.S05T01.chips.application;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.enums.GameState;
import cat.itacademy.s05.t01.S05T01.chips.data.Chips;
import cat.itacademy.s05.t01.S05T01.chips.data.SpringChipsRepository;
import cat.itacademy.s05.t01.S05T01.security.data.SpringUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
@Service
public class ChipsService {
    private final SpringUserRepository userRepository;
    private final SpringChipsRepository chipsRepository;

    public ChipsService(SpringUserRepository userRepository, SpringChipsRepository chipsRepository) {
        this.userRepository = userRepository;
        this.chipsRepository = chipsRepository;
    }

    public Mono<Chips> findBalance(String username) {
        return this.userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(username)))
                .flatMap(user -> {
                    System.out.println(username + " requested balance");
                    return this.chipsRepository.findByUser(user)
                            .switchIfEmpty(Mono.error(new RuntimeException("Chips not found for user: " + username)));
                });
    }

    public Mono<Chips> depositChips(String username, Long amount) {
        return this.userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(username)))  // Handle case where user is not found
                .flatMap(user ->
                        this.chipsRepository.findByUser(user)
                                .defaultIfEmpty(new Chips(user, 0L))  // Provide default Chips object if not found
                                .doOnNext(chips -> chips.deposit(amount))  // Update the Chips object
                                .flatMap(chips ->
                                        this.chipsRepository.save(chips)  // Save the updated Chips object
                                                .then(Mono.just(chips))  // Return the updated Chips object
                                )
                )
                .doOnNext(chips -> System.out.println(amount + " chips have been deposited for " + username));  // Log the deposit
    }

    public Mono<Void> withdrawChips(String username, Long amount) {
        return this.userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(username)))  // Handle user not found
                .flatMap(user ->
                        this.chipsRepository.findByUser(user)
                                .defaultIfEmpty(new Chips(user, 0L))  // Provide default Chips if not found
                                .flatMap(chips -> {
                                    chips.remove(amount);  // Perform the withdrawal
                                    return this.chipsRepository.save(chips)  // Save the updated Chips
                                            .then(Mono.fromRunnable(() ->  // Log the result
                                                    System.out.println(amount + " chips have been withdrawn for " + username)
                                            ));
                                })
                );
    }

    public Mono<Void> payOut(String username, GameState gameState, Long bet) {
        return this.userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(username)))  // Handle user not found
                .flatMap(user ->
                        this.chipsRepository.findByUser(user)
                                .defaultIfEmpty(new Chips(user, 0L))  // Provide default Chips if not found
                                .flatMap(chips -> {
                                    // PAYOUTS
                                    switch (gameState) {
                                        case PLAYERBLACKJACK:
                                            chips.deposit(bet * 5);
                                            System.out.println("WON BY BLACKJACK, you've won " + bet * 5 + " chips!, your new total is: " + chips.getAmount() + " chips!");
                                            break;
                                        case PLAYERPUSH:
                                            chips.deposit(bet);
                                            System.out.println("DRAW, your bet of " + bet + " chips has been returned, your new total is: " + chips.getAmount() + " chips!");
                                            break;
                                        case PLAYERNORMALWIN:
                                            chips.deposit(bet * 2);
                                            System.out.println("WIN, you've won " + bet * 2 + " chips!, your new total is: " + chips.getAmount() + " chips!");
                                            break;
                                        case PLAYERDOUBLE:
                                            chips.deposit(bet * 2);
                                            System.out.println("WON BY DOUBLING, you've won " + bet * 2 + " chips!, your new total is: " + chips.getAmount() + " chips!");
                                            break;
                                        case PLAYERSURRENDER:
                                            chips.deposit(bet / 2);
                                            System.out.println("SURRENDER, returned " + bet / 2 + " chips!, your new total is: " + chips.getAmount() + " chips!");
                                            break;
                                        case PLAYERLOSE:
                                            System.out.println("LOSE, you've lost " + bet + " chips!, your new total is: " + chips.getAmount() + " chips!");
                                            break;
                                    }
                                    return this.chipsRepository.save(chips)  // Save the updated Chips
                                            .then(Mono.empty());  // Complete the Mono<Void> chain
                                })
                );
    }

}


