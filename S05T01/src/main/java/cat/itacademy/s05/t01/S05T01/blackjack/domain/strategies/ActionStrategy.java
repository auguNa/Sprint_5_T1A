package cat.itacademy.s05.t01.S05T01.blackjack.domain.strategies;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.BlackjackGame;

public interface ActionStrategy {
    boolean doAction(BlackjackGame blackjackGame);
}


