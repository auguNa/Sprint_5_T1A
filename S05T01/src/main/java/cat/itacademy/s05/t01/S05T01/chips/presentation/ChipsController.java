package cat.itacademy.s05.t01.S05T01.chips.presentation;

import cat.itacademy.s05.t01.S05T01.chips.application.ChipsService;
import cat.itacademy.s05.t01.S05T01.chips.presentation.dto.BalanceDTO;
import cat.itacademy.s05.t01.S05T01.chips.presentation.dto.DepositDTO;
import cat.itacademy.s05.t01.S05T01.security.data.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/chips")
public class ChipsController {
    private final ChipsService service;

    public ChipsController(ChipsService service) {
        this.service = service;
    }

    @GetMapping("/balance")
    public Mono<BalanceDTO> showBalance(Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return this.service.findBalance(profile.getUsername())
                .map(chips -> new BalanceDTO(
                        chips.getUser().getUsername(),
                        chips.getLastUpdate(),
                        chips.getAmount()
                ))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found")));
    }

    @PostMapping("/deposit")
    public void deposit(Authentication authentication, @RequestBody DepositDTO deposit) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        this.service.depositChips(profile.getUsername(), deposit.amount);
    }
}


