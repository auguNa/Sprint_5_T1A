package cat.itacademy.s05.t01.S05T01.blackjack.presentation;

import cat.itacademy.s05.t01.S05T01.blackjack.application.BlackjackService;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.BlackjackGame;
import cat.itacademy.s05.t01.S05T01.blackjack.presentation.dto.BetDTO;
import cat.itacademy.s05.t01.S05T01.blackjack.presentation.dto.BlackjackDTO;
import cat.itacademy.s05.t01.S05T01.security.data.UserProfile;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/game")
public class BlackJackController {

    private final BlackjackService blackJackService;

    public BlackJackController(BlackjackService blackJackService) { this.blackJackService = blackJackService; }

    @PostMapping("/startgame")
    public BlackjackDTO startGame(Authentication authentication, @RequestBody BetDTO bet) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        var blackjackGame = blackJackService.startOrContinueGame(profile.getUsername(), bet.betAmount);
        return createBlackjackDTO(blackjackGame);
    }

    @PostMapping("/hit")
    public BlackjackDTO playerHit(Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        var blackjackGame = blackJackService.playerHit(profile.getUsername());
        return createBlackjackDTO(blackjackGame);
    }

    @PostMapping("/stand")
    public BlackjackDTO playerStand(Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        var blackjackGame = blackJackService.playerStandOrDealersTurn(profile.getUsername());
        return createBlackjackDTO(blackjackGame);
    }

    @PostMapping("/surrender")
    public BlackjackDTO playerSurrender(Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        var blackjackGame = blackJackService.playerSurrender(profile.getUsername());
        return createBlackjackDTO(blackjackGame);
    }

    @PostMapping("/double")
    public BlackjackDTO playerDouble(Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        var blackjackGame = blackJackService.playerDouble(profile.getUsername());
        return createBlackjackDTO(blackjackGame);
    }

    private BlackjackDTO createBlackjackDTO(BlackjackGame blackjackGame) {
        return new BlackjackDTO(
                blackjackGame.getGameState(),
                blackjackGame.getPlayer().getHand(),
                blackjackGame.getDealer().getHand());
    }
}
