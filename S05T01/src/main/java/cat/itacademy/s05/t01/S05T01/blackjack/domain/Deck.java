package cat.itacademy.s05.t01.S05T01.blackjack.domain;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.enums.Rank;
import cat.itacademy.s05.t01.S05T01.blackjack.domain.enums.Suit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Deck implements Serializable {
    private List<Card> cards = new ArrayList<>();

    public Deck() {
        fillDeck();
    }

    private void fillDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public Card getFirstCardAndRemoveOutOfDeck() {
        Card firstCard = this.cards.get(0);
        this.cards.remove(0);
        return firstCard;
    }

    public List<Card> getCards() {
        return cards;
    }

}


