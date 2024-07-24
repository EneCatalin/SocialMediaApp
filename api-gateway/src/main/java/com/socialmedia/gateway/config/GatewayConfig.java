//package com.socialmedia.gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("users-service", r -> r.path("/users/**")
//                        .uri("http://localhost:8082")
//                        .filter((exchange, chain) -> {
//                            // Custom filter logic here if needed
//                            return chain.filter(exchange);
//                        }))
//                .route("posts-service", r -> r.path("/posts/**")
//                        .uri("http://localhost:8081")
//                        .filter((exchange, chain) -> {
//                            // Custom filter logic here if needed
//                            return chain.filter(exchange);
//                        }))
//                .build();
//    }
//}
//
