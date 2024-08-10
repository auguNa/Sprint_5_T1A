package cat.itacademy.s05.t01.S05T01.security.application;

import cat.itacademy.s05.t01.S05T01.chips.data.Chips;
import cat.itacademy.s05.t01.S05T01.chips.data.SpringChipsRepository;
import cat.itacademy.s05.t01.S05T01.security.data.SpringUserRepository;
import cat.itacademy.s05.t01.S05T01.security.data.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;


/**
 * Implements UserDetailsService in order to make it usable
 * as login/registration service for Spring.
 * (see AuthenticationFilter)
 */
@Service
@Transactional
public class UserService implements ReactiveUserDetailsService {
    private final SpringUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SpringChipsRepository chipsRepository;

    @Value("${chips.start-amount}")
    private Long chipsStartAmount;


    public UserService(SpringUserRepository repository, PasswordEncoder passwordEncoder, SpringChipsRepository chipsRepository) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.chipsRepository = chipsRepository;
    }

    public Mono<Void> register(String username, String password, String firstName, String lastName) {
        String encodedPassword = this.passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, firstName, lastName);
        Chips chips = new Chips(user, chipsStartAmount);

        return this.userRepository.save(user)
                .then(this.chipsRepository.save(chips))
                .doOnSuccess(saved -> System.out.println("New user: " + username + " has been created"))
                .then();
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found: " + username)))
                .cast(UserDetails.class);
    }
}


