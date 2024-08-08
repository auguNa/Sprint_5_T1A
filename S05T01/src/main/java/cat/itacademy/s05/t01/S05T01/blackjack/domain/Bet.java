package cat.itacademy.s05.t01.S05T01.blackjack.domain;

import java.io.Serializable;

public class Bet implements Serializable {
    private Long amount;

    public Bet(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
