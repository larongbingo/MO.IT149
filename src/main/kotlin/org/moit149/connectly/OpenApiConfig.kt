package org.moit149.connectly

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
        .addSecurityItem(SecurityRequirement().addList("googleOauth"))
        .components(
            Components().addSecuritySchemes(
                "googleOauth",
                SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                    .flows(
                        OAuthFlows().authorizationCode(
                            OAuthFlow()
                                .authorizationUrl("https://accounts.google.com/o/oauth2/v2/auth")
                                .tokenUrl("https://oauth2.googleapis.com/token")
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