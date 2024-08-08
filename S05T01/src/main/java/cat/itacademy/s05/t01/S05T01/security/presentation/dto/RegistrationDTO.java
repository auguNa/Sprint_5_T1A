package cat.itacademy.s05.t01.S05T01.security.presentation.dto;


import jakarta.validation.constraints.*;

public class RegistrationDTO {
    @NotBlank
    public String username;

    @Size(min = 5)
    public String password;

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;
}

