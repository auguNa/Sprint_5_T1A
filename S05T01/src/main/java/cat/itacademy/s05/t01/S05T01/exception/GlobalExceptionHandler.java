package cat.itacademy.s05.t01.S05T01.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<Void> handleUsernameNotFound(ServerWebExchange exchange, UsernameNotFoundException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
        return Mono.empty();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Void> handleRuntimeException(ServerWebExchange exchange, RuntimeException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        return Mono.empty();
    }
}