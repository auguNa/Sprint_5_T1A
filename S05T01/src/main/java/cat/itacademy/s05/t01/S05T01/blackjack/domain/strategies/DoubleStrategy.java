package cat.itacademy.s05.t01.S05T01.blackjack.domain.strategies;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.BlackjackGame;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.enums.GameState;

public class DoubleStrategy implements ActionStrategy {
    @Override
    public boolean doAction(BlackjackGame blackjackGame) {
        if (blackjackGame.getGameState() == GameState.STARTOFGAME) {

            blackjackGame.getBet().setAmount(blackjackGame.getBet().getAmount() * 2);

            blackjackGame.getDealer().drawSingleCard(blackjackGame.getPlayer());

            System.out.println("Your cards: " + blackjackGame.getPlayer().getHand().getCards());

            blackjackGame.setGameState(GameState.PLAYERDOUBLE);

            return true;
        }
        return false;
    }
}
