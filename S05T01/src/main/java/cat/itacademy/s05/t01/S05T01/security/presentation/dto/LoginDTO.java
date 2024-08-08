package cat.itacademy.s05.t01.S05T01.security.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
    @NotBlank
    public String username;

    @NotBlank
    public String password;
}

