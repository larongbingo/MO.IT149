package org.moit149.connectly.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    /**
     * Public endpoints: root + Swagger/OpenAPI.
     * No oauth2Login() here => these URLs will never redirect to OAuth.
     */
    @Bean
    @Order(1)
    fun publicFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher(
                "/",
                "/index.html",
                "/error",
                "/api-docs",
                "/api-docs/**",
                "/swagger-ui",
                "/swagger-ui/**",
                "/swagger-ui.html"
            )
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll()
            }
            .csrf { it.disable() }

        return http.build()
    }

    /**
     * Everything else: protected by OAuth2 login.
     */
    @Bean
    @Order(2)
    fun appFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth.anyRequest().authenticated()
            }
            .oauth2Login { }
            .logout { }
            .csrf { it.disable() }

        return http.build()
    }
}