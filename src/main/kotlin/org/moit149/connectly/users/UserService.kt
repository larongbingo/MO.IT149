package org.moit149.connectly.users

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

@Service
class UserService(private val auth0Service: Auth0Service, private val userRepository: UserRepository) {
    fun createConnectlyProfile(authentication: Authentication, newUser: NewUserDto): User? {
        val externalId = authentication.principal as Jwt
        val externalIdClaim = externalId.claims["sub"] as String?

        val user = userRepository.findByExternalId(externalIdClaim ?: return null)
        if (user != null) return null

        val userDetails = auth0Service.getUserProfile(externalId.tokenValue)
        userDetails ?: return null

        val newUser = User(
            id = null,
            externalId = externalIdClaim,
            firstName = userDetails.given_name,
            lastName = userDetails.family_name,
            email = userDetails.email,
            picture = userDetails.picture,
            displayName = newUser.displayName,
            title = newUser.title
        )

        return userRepository.save(newUser)
    }

    fun getConnectlyProfile(authentication: Authentication): User? {
        val externalId = authentication.principal as Jwt
        val externalIdClaim = externalId.claims["sub"] as String?
        return userRepository.findByExternalId(externalIdClaim ?: return null)
    }
}