package cat.itacademy.s05.t01.S05T01.blackjack.application;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.BlackjackGame;

public interface BlackjackGameFactory {
    BlackjackGame create(Long bet);
}
