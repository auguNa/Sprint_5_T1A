package cat.itacademy.s05.t01.S05T01.blackjack.domain;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.enums.Rank;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.enums.Suit;

import java.io.Serializable;

public class Card implements Serializable {
    private Rank rank;
    private Suit suit;
    private int value;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.value = rank.rank();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return " ["
                + rank
                + "/"
                + suit.suit()
                + "]("
                + value
                + ") ";
    }
}


