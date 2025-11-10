package br.edu.insper.exercicio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/assets/**", "/static/**").permitAll()

                        .requestMatchers(HttpMethod.DELETE, "/api/films/**").hasRole("admin")

                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<String> roles = new ArrayList<>();

            Object rolesClaim = jwt.getClaim("roles");
            if (rolesClaim instanceof Collection) {
                ((Collection<?>) rolesClaim).forEach(r -> roles.add(String.valueOf(r)));
            }

            Object namespaced = jwt.getClaim("https://example.com/roles");
            if (namespaced instanceof Collection) {
                ((Collection<?>) namespaced).forEach(r -> roles.add(String.valueOf(r)));
            }

            Object realmAccess = jwt.getClaim("realm_access");
            if (realmAccess instanceof Map) {
                Object r = ((Map<?, ?>) realmAccess).get("roles");
                if (r instanceof Collection) {
                    ((Collection<?>) r).forEach(x -> roles.add(String.valueOf(x)));
                }
            }

            return roles.stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                    .collect(Collectors.toList());
        });
        return converter;
    }
}
