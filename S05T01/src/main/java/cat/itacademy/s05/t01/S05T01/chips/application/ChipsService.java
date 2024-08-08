package cat.itacademy.s05.t01.S05T01.chips.application;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.enums.GameState;
import cat.itacademy.s05.t01.S05T01.chips.data.Chips;
import cat.itacademy.s05.t01.S05T01.chips.data.SpringChipsRepository;
import cat.itacademy.s05.t01.S05T01.security.data.SpringUserRepository;
import cat.itacademy.s05.t01.S05T01.security.data.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class ChipsService {
    private final SpringUserRepository userRepository;
    private final SpringChipsRepository chipsRepository;

    public ChipsService(SpringUserRepository userRepository, SpringChipsRepository chipsRepository) {
        this.userRepository = userRepository;
        this.chipsRepository = chipsRepository;
    }

    public Optional<Chips> findBalance(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        System.out.println(username + " requested balance");
        return this.chipsRepository.findByUser(user);
    }

    public void depositChips(String username, Long amount) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Chips chips = this.chipsRepository.findByUser(user)
                .orElse(new Chips(user, 0L));
        chips.deposit(amount);

        System.out.println(amount + " chips have been deposited for " + username);
        this.chipsRepository.save(chips);
    }

    public void withdrawChips(String username, Long amount) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Chips chips = this.chipsRepository.findByUser(user)
                .orElse(new Chips(user, 0L));
        chips.remove(amount);

        System.out.println(amount + " chips have been withdrawed for " + username);
        this.chipsRepository.save(chips);
    }

    public void payOut(String username, GameState gameState, Long bet) {
        var user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        var chips = this.chipsRepository.findByUser(user)
                .orElse(new Chips(user, 0L));

        //PAYOUTS
        if (gameState == GameState.PLAYERBLACKJACK) {
            chips.deposit(bet * 5);
            System.out.println("WON BY BLACKJACK, you've won " + bet * 5 + " chips!, your new total is: " + chips.getAmount() + " chips!");
        } else if (gameState == GameState.PLAYERPUSH) {
            chips.deposit(bet);
            System.out.println("DRAW, your bet of " + bet + " chips has been returned, your new total is: " + chips.getAmount() + " chips!");
        } else if (gameState == GameState.PLAYERNORMALWIN) {
            chips.deposit(bet * 2);
            System.out.println("WIN, you've won " + bet * 2 + " chips!, your new total is: " + chips.getAmount() + " chips!");
        } else if (gameState == GameState.PLAYERDOUBLE) {
            chips.deposit(bet * 2);
            System.out.println("WON BY DOUBLING, you've won " + bet * 2 + " chips!, your new total is: " + chips.getAmount() + " chips!");
        } else if (gameState == GameState.PLAYERSURRENDER) {
            chips.deposit(bet / 2);
            System.out.println("SURRENDER, returned " + bet / 2 + " chips!, your new total is: " + chips.getAmount() + " chips!");
        } else if (gameState == GameState.PLAYERLOSE) {
            //deposit nothing because of lose
            System.out.println("LOSE, you've lost " + bet + " chips!, your new total is: " + chips.getAmount() + " chips!");
        }
        this.chipsRepository.save(chips);
    }
}


