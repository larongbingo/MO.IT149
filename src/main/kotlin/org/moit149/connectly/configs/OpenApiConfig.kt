package org.moit149.connectly.configs

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.OAuthFlow
import io.swagger.v3.oas.models.security.OAuthFlows
import io.swagger.v3.oas.models.security.Scopes
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun connectlyOpenApiConfig(): OpenAPI = OpenAPI()
        .addSecurityItem(SecurityRequirement().addList("auth0"))
        .components(
            Components().addSecuritySchemes(
                "auth0",
                SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                    .flows(
                        OAuthFlows().authorizationCode(
                            OAuthFlow()
                                .authorizationUrl("https://ewan.au.auth0.com/authorize")
                                .tokenUrl("https://ewan.au.auth0.com/oauth/token")
                                .scopes(
                                    Scopes()
                                        .addString("openid", "OpenID")
                                        .addString("profile", "Profile")
                                        .addString("email", "Email")
                                )
                        )
                    )
            )
        )
}