package cat.itacademy.s05.t01.S05T01.security;


import cat.itacademy.s05.t01.S05T01.security.presentation.filter.JwtAuthenticationFilter;
import cat.itacademy.s05.t01.S05T01.security.presentation.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    public final static String LOGIN_PATH = "/login";
    public final static String REGISTER_PATH = "/register";

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-in-ms}")
    private Integer jwtExpirationInMs;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable) // Use the new method to configure CORS
                .csrf(AbstractHttpConfigurer::disable) // Use the new method to configure CSRF
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST, REGISTER_PATH).permitAll()
                        .requestMatchers(HttpMethod.POST, LOGIN_PATH).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(
                                LOGIN_PATH,
                                this.jwtSecret,
                                this.jwtExpirationInMs,
                                http.getSharedObject(AuthenticationManager.class)
                        ),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilter(new JwtAuthorizationFilter(this.jwtSecret, http.getSharedObject(AuthenticationManager.class)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}