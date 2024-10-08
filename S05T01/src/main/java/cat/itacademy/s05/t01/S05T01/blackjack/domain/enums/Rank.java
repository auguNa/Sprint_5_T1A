package cat.itacademy.s05.t01.S05T01.blackjack.domain.enums;

public enum Rank {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    KING(10),
    QUEEN(10),
    JACK(10),
    ACE(11);

    private int rank;

    Rank(int i) {
        this.rank = i;
    }

    public int rank() {
        return rank;
    }
}
