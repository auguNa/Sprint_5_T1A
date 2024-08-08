package cat.itacademy.s05.t01.S05T01.blackjack.domain.enums;

public enum Suit {
    CLUBS('♣'),
    DIAMONDS('♦'),
    HEARTS('♥'),
    SPADES('♠');

    private char suit;

    Suit(char c) {
        this.suit = c;
    }

    public char suit() {
        return suit;
    }
}
