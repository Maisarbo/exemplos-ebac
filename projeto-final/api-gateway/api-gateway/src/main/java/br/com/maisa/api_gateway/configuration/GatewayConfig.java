package br.com.maisa.api_gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("meme", r -> r.path("/memes/**")
                        .uri("http://localhost:8083"))
                .route("categoria-meme", r -> r.path("/categoria-meme/**")
                        .uri("http://localhost:8082"))
                .route("usuario", r -> r.path("/usuarios/**")
                        .uri("http://localhost:8081"))
                .build();
    }

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }
}
