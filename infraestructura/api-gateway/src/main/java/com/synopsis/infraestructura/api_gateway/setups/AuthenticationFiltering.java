package com.synopsis.infraestructura.api_gateway.setups;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class AuthenticationFiltering extends AbstractGatewayFilterFactory<AuthenticationFiltering.Config> {

    final Logger logger = LoggerFactory.getLogger(GlobalPostFiltering.class);
    private final WebClient.Builder webclientBuilder;

    public AuthenticationFiltering(WebClient.Builder webBuilder) {
        super(Config.class);
        this.webclientBuilder = webBuilder;
    }

    @SuppressWarnings("deprecation")
    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            logger.info("mis validaciones");
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing  Authorization header");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad Authorization structure");
            }
            logger.info("uhh token con fomarmato OK");
            return webclientBuilder.build()
                    .get()
                    .uri("http://KEYCLOAK-ADAPTER/roles").header(HttpHeaders.AUTHORIZATION, parts[1])
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .map(response -> {
                        if (response != null) {
                            logger.info("See Objects: " + response);
                            // check for Partners rol
                            if (response.get("admin02") == null || StringUtils.isEmpty(response.get("admin02").asText())) {
                                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Role Partners missing");
                            }
                            logger.info("TODO OK ");
                        } else {
                            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Roles missing");
                        }
                        logger.info("VA A RETORNAR :" + exchange);
                        return exchange;
                    })
                    .onErrorMap(error -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Communication Error",
                                error.getCause());
                    })
                    .flatMap(chain::filter);
        }, 1);
    }

    public static class Config {

    }

}
