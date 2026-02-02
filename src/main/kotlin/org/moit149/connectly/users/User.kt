package org.moit149.connectly.users

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    @Id val id: String?,
    @Indexed(unique = true) val externalId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val picture: String,
    var displayName: String,
    var title: String?,
) {
    fun toUserDto(): UserDto = UserDto(email, firstName, lastName, picture, displayName, title)
}

data class UserDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val picture: String,
    val displayName: String,
    val title: String?
)