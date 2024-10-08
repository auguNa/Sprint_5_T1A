package cat.itacademy.s05.t01.S05T01.chips.presentation.dto;

import java.util.Date;


public class BalanceDTO {
    private final String username;
    private final Date lastUpdate;
    private final Long chips;

    public BalanceDTO(String username, Date lastUpdate, Long chips) {
        this.username = username;
        this.lastUpdate = lastUpdate;
        this.chips = chips;
    }

    public String getUsername() {
        return username;
    }

    public Long getChips() {
        return chips;
    }
}
