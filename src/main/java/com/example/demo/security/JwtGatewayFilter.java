
package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        System.out.println("Path richiesto: " + path); // Debug

        // Percorsi che NON richiedono autenticazione
        if (path.equals("/auth/login") ||
                path.startsWith("/actuator/")) {
            return chain.filter(exchange);
        }

        // Percorsi che RICHIEDONO autenticazione JWT
        if (path.startsWith("/corsi/") ||
                path.startsWith("/discenti/") ||
                path.startsWith("/docenti/")) {

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                try {
                    String username = jwtUtil.extractUsername(token);

                    if (username != null && jwtUtil.validateToken(token, username)) {
                        System.out.println("Token valido per utente: " + username); // Debug
                        return chain.filter(exchange);
                    }
                } catch (Exception e) {
                    System.err.println("Errore JWT: " + e.getMessage());
                }
            }

            // Token non valido o assente per percorsi protetti
            System.err.println("Accesso negato per path: " + path); // Debug
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Per tutti gli altri percorsi, permetti l'accesso
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}