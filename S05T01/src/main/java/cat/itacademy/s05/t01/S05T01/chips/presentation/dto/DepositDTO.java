package cat.itacademy.s05.t01.S05T01.chips.presentation.dto;

import jakarta.validation.constraints.Positive;

public class DepositDTO {
    @Positive
    public Long amount;
}

