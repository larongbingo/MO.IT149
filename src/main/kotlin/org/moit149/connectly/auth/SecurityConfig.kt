package org.moit149.connectly.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer = object : WebMvcConfigurer {
        override fun addCorsMappings(registry: org.springframework.web.servlet.config.annotation.CorsRegistry) {
            registry.addMapping("/**").allowedOrigins("*")
        }
    }

    /**
     * Public endpoints: root + Swagger/OpenAPI.
     * No oauth2Login() here => these URLs will never redirect to OAuth.
     */
    @Bean
    @Order(1)
    fun publicFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors(Customizer.withDefaults())
            .securityMatcher(
                "/",
                "/index.html",
                "/error",
                "/api-docs",
                "/oauth2/**",
                "/login/oauth2/**",
                "/api-docs/**",
                "/swagger-ui",
                "/swagger-ui/**",
                "/swagger-ui.html"
            )
            .oauth2ResourceServer { it.jwt {} }
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
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests { auth ->
                auth.anyRequest().authenticated()
            }
            .oauth2ResourceServer { it.jwt {} }
            .logout { }
            .csrf { it.disable() }

        return http.build()
    }
}