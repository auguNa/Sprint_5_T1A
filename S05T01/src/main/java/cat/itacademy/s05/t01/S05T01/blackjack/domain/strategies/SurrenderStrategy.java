package cat.itacademy.s05.t01.S05T01.blackjack.domain.strategies;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.BlackjackGame;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.enums.GameState;

public class SurrenderStrategy implements ActionStrategy{
    @Override
    public boolean doAction(BlackjackGame blackjackGame) {
        blackjackGame.setGameState(GameState.PLAYERSURRENDER);
        return true;
    }
}
