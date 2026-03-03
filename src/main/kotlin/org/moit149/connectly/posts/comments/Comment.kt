package org.moit149.connectly.posts.comments

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "comments")
data class Comment(
    @Id val id: String,
    @Indexed(unique = false) val postId: String,
    @Indexed(unique = false) val userId: String,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var likes: Int = 0
)