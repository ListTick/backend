package com.pro.list_tick.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiSecurityConfig {

    private static final String SCHEME_NAME = "keycloak";

    @Value("${keycloak.auth.server.url}")
    String authServerUrl;

    @Value("${keycloak.realm}")
    String realm;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME, keycloakScheme()))
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .info(new Info().title("ListTick Service").version("1.0"));
    }

    private SecurityScheme keycloakScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(authorizationCodeFlows());
    }

    private OAuthFlows authorizationCodeFlows() {
        return new OAuthFlows().authorizationCode(authorizationCodeFlow());
    }

    private OAuthFlow authorizationCodeFlow() {
        String base = authServerUrl + "/realms/" + realm + "/protocol/openid-connect";
        return new OAuthFlow()
                .authorizationUrl(base + "/auth")
                .tokenUrl(base + "/token")
                .refreshUrl(base + "/token")
                .scopes(new Scopes()
                        .addString("openid",  "OpenID")
                        .addString("profile", "User profile")
                        .addString("email",   "E-mail"));
    }
}
