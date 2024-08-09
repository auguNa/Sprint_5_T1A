package cat.itacademy.s05.t01.S05T01;

import cat.itacademy.s05.t01.S05T01.blackjack.application.BlackjackGameFactory;
import cat.itacademy.s05.t01.S05T01.blackjack.application.BlackjackService;
import cat.itacademy.s05.t01.S05T01.blackjack.data.BlackjackRepository;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.BlackjackGame;
import cat.itacademy.s05.t01.S05T01.chips.application.ChipsService;
import cat.itacademy.s05.t01.S05T01.security.data.SpringUserRepository;
import cat.itacademy.s05.t01.S05T01.security.data.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/*@SpringBootTest
public class BlackjackServiceTest {

	@Mock
	private SpringUserRepository userRepository;

	@Mock
	private BlackjackRepository blackjackRepository;

	@Mock
	private ChipsService chipsService;

	@Mock
	private BlackjackGameFactory blackjackGameFactory;

	@InjectMocks
	private BlackjackService blackjackService;

	@Test
	public void testStartOrContinueGame() {
		User user = new User("testUser", "password");
		BlackjackGame blackjackGame = new BlackjackGame();
		when(userRepository.findByUsername("testUser")).thenReturn(Mono.just(user));
		when(blackjackRepository.findTopByUserAndGameDoneOrderByIdDesc(user, false)).thenReturn(Mono.empty());
		when(blackjackGameFactory.create(50L)).thenReturn(blackjackGame);
		when(chipsService.withdrawChips("testUser", 50L)).thenReturn(Mono.empty());

		BlackjackGame result = blackjackService.startOrContinueGame("testUser", 50L).block();

		assertNotNull(result);
		verify(userRepository, times(1)).findByUsername("testUser");
		verify(blackjackRepository, times(1)).findTopByUserAndGameDoneOrderByIdDesc(user, false);
		verify(blackjackGameFactory, times(1)).create(50L);
	}
}*/