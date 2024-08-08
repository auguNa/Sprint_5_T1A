package cat.itacademy.s05.t01.S05T01.security.presentation.controller;


import cat.itacademy.s05.t01.S05T01.security.application.UserService;
import cat.itacademy.s05.t01.S05T01.security.presentation.dto.RegistrationDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void register(@Validated @RequestBody RegistrationDTO registration) {
        this.userService.register(
                registration.username,
                registration.password,
                registration.firstName,
                registration.lastName
        );
    }
}

