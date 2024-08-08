package cat.itacademy.s05.t01.S05T01.blackjack.presentation.dto;


import jakarta.validation.constraints.Positive;

public class BetDTO {
    @Positive
    public Long betAmount;
}
