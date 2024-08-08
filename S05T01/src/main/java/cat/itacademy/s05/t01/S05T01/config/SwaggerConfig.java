package cat.itacademy.s05.t01.S05T01.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Blackjack API", version = "1.0", description = "Blackjack Game API"))
public class SwaggerConfig {
}