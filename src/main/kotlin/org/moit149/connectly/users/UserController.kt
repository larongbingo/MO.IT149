package org.moit149.connectly.users

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    @GetMapping
    fun getProfile(authentication: Authentication): ResponseEntity<UserDto> {
        val user = userService.getConnectlyProfile(authentication)
        if (user != null) return ResponseEntity.ok(user.toUserDto())
        return ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createUser(authentication: Authentication, @RequestBody newUser: NewUserDto): ResponseEntity<UserDto> {
        val newUserEntity = userService.createConnectlyProfile(authentication, newUser)

        if (newUserEntity != null)
            return ResponseEntity.ok(newUserEntity.toUserDto())

        return ResponseEntity.badRequest().build()
    }
}

data class NewUserDto(
    val displayName: String,
    val title: String,
    val major: String,
    val year: String,
    val school: String
)