package cat.itacademy.s05.t01.S05T01;

import cat.itacademy.s05.t01.S05T01.blackjack.application.BlackjackService;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.BlackjackGame;
import cat.itacademy.s05.t01.S05T01.blackjack.presentation.BlackJackController;
import cat.itacademy.s05.t01.S05T01.blackjack.presentation.dto.BetDTO;
import cat.itacademy.s05.t01.S05T01.blackjack.presentation.dto.BlackjackDTO;
import cat.itacademy.s05.t01.S05T01.security.data.UserProfile;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

/* @SpringBootTest
public class BlackJackControllerTest {
    @Mock
    private BlackjackService blackjackService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private BlackJackController blackJackController;

    @Test
    public void testStartGame() {
        BetDTO betDTO = new BetDTO();
        betDTO.betAmount = 50L;

        when(authentication.getPrincipal()).thenReturn(new UserProfile("testUser", "ROLE_USER"));
        when(blackjackService.startOrContinueGame("testUser", 50L)).thenReturn(Mono.just(new BlackjackGame()));

        BlackjackDTO response = blackJackController.startGame(authentication, betDTO);

        assertNotNull(response);
        verify(blackjackService, times(1)).startOrContinueGame("testUser", 50L);
    }
}*/
