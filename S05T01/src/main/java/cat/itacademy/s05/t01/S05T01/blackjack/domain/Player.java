package cat.itacademy.s05.t01.S05T01.blackjack.domain;

import java.io.Serializable;

public class Player implements Serializable, Person {
    private Hand hand;

    public Player(Hand hand) {
        this.hand = hand;
    }

    @Override
    public int totalScoreOfCards() {
        int score = 0;
        for (Card card : this.hand.getCards()) {
            score += card.getValue();
        }
        return score;
    }

    @Override
    public Hand getHand() {
        return hand;
    }
}


