package cat.itacademy.s05.t01.S05T01.blackjack.application;

import cat.itacademy.s05.t01.S05T01.blackjack.data.BlackjackRepository;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.*;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.enums.GameState;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.strategies.*;
import cat.itacademy.s05.t01.S05T01.chips.application.ChipsService;
import cat.itacademy.s05.t01.S05T01.security.data.SpringUserRepository;
import cat.itacademy.s05.t01.S05T01.security.data.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cat.itacademy.s05.t01.S05T01.blackjack.data.Blackjack;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@Transactional
@Service
public class BlackjackService {

    private final SpringUserRepository userRepository;
    private final BlackjackRepository blackJackRepository;
    private final ChipsService chipsService;
    private final BlackjackGameFactory blackJackGameFactory;

    public BlackjackService(SpringUserRepository userRepository, BlackjackRepository blackJackRepository, ChipsService chipsService, BlackjackGameFactory blackJackGameFactory) {
        this.userRepository = userRepository;
        this.blackJackRepository = blackJackRepository;
        this.chipsService = chipsService;
        this.blackJackGameFactory = blackJackGameFactory;
    }

    public Mono<BlackjackGame> startOrContinueGame(String username, Long bet) {
        return retrieveUser(username)
                .flatMap(user -> blackJackRepository.findTopByUserAndGameDoneOrderByIdDesc(user, false)
                        .flatMap(blackjack -> {
                            BlackjackGame existingGame = blackjack.getBlackjackGame();
                            if (existingGame != null) {
                                return Mono.just(existingGame);
                            }
                            // No existing game found, create a new one
                            BlackjackGame newGame = blackJackGameFactory.create(bet);
                            return chipsService.withdrawChips(username, bet)
                                    .then(Mono.defer(() -> {
                                        if (new InitializeGameStrategy().doAction(newGame)) {
                                            return playerStandOrDealersTurn(username);
                                        }
                                        return Mono.just(newGame);
                                    }))
                                    .flatMap(finalGame -> blackJackRepository.save(new Blackjack(finalGame, false, user))
                                            .thenReturn(finalGame));
                        })
                        .switchIfEmpty(Mono.defer(() -> {
                            // No existing game found at all, create a new one
                            BlackjackGame newGame = blackJackGameFactory.create(bet);
                            return chipsService.withdrawChips(username, bet)
                                    .then(Mono.defer(() -> {
                                        if (new InitializeGameStrategy().doAction(newGame)) {
                                            return playerStandOrDealersTurn(username);
                                        }
                                        return Mono.just(newGame);
                                    }))
                                    .flatMap(finalGame -> blackJackRepository.save(new Blackjack(finalGame, false, user))
                                            .thenReturn(finalGame));
                                })
                        ));
    }

    public Mono<BlackjackGame> playerHit(String username) {
        return retrieveBlackjack(username) // Get the current Blackjack object as Mono
                .flatMap(blackjack -> {
                    var blackjackGame = blackjack.getBlackjackGame(); // Retrieve the BlackjackGame from the Blackjack object

                    // Apply the HitStrategy
                    if (new HitStrategy().doAction(blackjackGame)) {
                        // Check if the game state requires standing or dealers turn
                        if (blackjackGame.getGameState() == GameState.PLAYERLOSE) {
                            // Chain to the playerStandOrDealersTurn method
                            return playerStandOrDealersTurn(username);
                        }

                        // Save the Blackjack object asynchronously and return the updated BlackjackGame
                        return this.blackJackRepository.save(blackjack)
                                .thenReturn(blackjackGame);
                    } else {
                        // Return an error if the hit action is not allowed
                        return Mono.error(new RuntimeException("♣ ♦ ♥ ♠ Can't hit at this moment! ♠ ♥ ♦ ♣"));
                    }
                });
    }


    public Mono<BlackjackGame> playerStandOrDealersTurn(String username) {
        return retrieveBlackjack(username).flatMap(blackjack -> {
            var blackjackGame = blackjack.getBlackjackGame();
            new StandOrDealersTurnStrategy().doAction(blackjackGame);
            return this.chipsService.payOut(username, blackjackGame.getGameState(), blackjackGame.getBet().getAmount())
                    .then(Mono.defer(() -> {
                        blackjack.setGameDone(true);
                        return this.blackJackRepository.save(blackjack).thenReturn(blackjackGame);
                    }));
        });
    }

    public Mono<BlackjackGame> playerSurrender(String username) {
        return retrieveBlackjack(username).flatMap(blackjack -> {
            var blackjackGame = blackjack.getBlackjackGame();
            new SurrenderStrategy().doAction(blackjackGame);
            return playerStandOrDealersTurn(username);
        });
    }

    public Mono<BlackjackGame> playerDouble(String username) {
        return retrieveBlackjack(username).flatMap(blackjack -> {
            var blackjackGame = blackjack.getBlackjackGame();
            return this.chipsService.withdrawChips(username, blackjackGame.getBet().getAmount()).then(Mono.defer(() -> {
                if (new DoubleStrategy().doAction(blackjackGame)) {
                    return this.blackJackRepository.save(blackjack).thenReturn(playerStandOrDealersTurn(username).block());
                }
                return Mono.error(new RuntimeException("Can't double at this moment!"));
            }));
        });
    }

    private Mono<User> retrieveUser(String username) {
        return this.userRepository.findByUsername(username).switchIfEmpty(Mono.error(new UsernameNotFoundException("User with username: " + username + " does not exist!")));
    }

    private Mono<Blackjack> retrieveBlackjack(String username) {
        return retrieveUser(username).flatMap(user ->
                this.blackJackRepository.findTopByUserAndGameDoneOrderByIdDesc(user, false).switchIfEmpty(Mono.error(new RuntimeException("Game has not started yet!")))
        );
    }
}