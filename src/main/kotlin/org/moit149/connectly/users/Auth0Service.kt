package org.moit149.connectly.users

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.time.LocalDateTime

@Service
class Auth0Service(val restBuilder: RestClient.Builder) {
    private val restClient: RestClient by lazy { restBuilder.baseUrl("https://ewan.au.auth0.com/").build() }

    fun getUserProfile(accessToken: String) =
        restClient
            .get()
            .uri { it.path("/userinfo").build() }
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .body<Auth0UserProfile>()
}

data class Auth0UserProfile(
    val sub: String,
    val given_name: String,
    val family_name: String,
    val nickname: String,
    val name: String,
    val picture: String,
    val updated_at: LocalDateTime,
    val email: String,
    val email_verified: Boolean
)