package cat.itacademy.s05.t01.S05T01.blackjack.presentation.dto;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.Hand;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.enums.GameState;

public class BlackjackDTO {
    public GameState gameState;
    public Hand playerHand;
    public Hand dealerHand;

    public BlackjackDTO(GameState gameState, Hand playerHand, Hand dealerHand) {
        this.gameState = gameState;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
    }
}
