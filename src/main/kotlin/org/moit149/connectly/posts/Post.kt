package org.moit149.connectly.posts

import org.moit149.connectly.users.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "posts")
data class Post(
    @Id val id: String?,
    @Indexed(unique = false) val userId: String,
    val message: String,
    var likes: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

data class PostDto(
    val id: String,
    val user: UserDto,
    val message: String,
    val likes: Int,
    val createdAt: LocalDateTime
)
