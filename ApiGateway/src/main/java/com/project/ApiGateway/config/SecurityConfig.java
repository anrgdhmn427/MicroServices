package com.project.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

@SuppressWarnings("removal")
@Configuration
@EnableWebFlux
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange().
                anyExchange().
                authenticated().
                and().
                oauth2Client().
                and().
                oauth2ResourceServer().
                jwt();

        return serverHttpSecurity.build();
    }

}
