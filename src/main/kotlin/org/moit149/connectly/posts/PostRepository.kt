package org.moit149.connectly.posts

import org.moit149.connectly.users.User
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

interface PostRepository : MongoRepository<Post, String>, CustomPostRepository

interface CustomPostRepository {
    fun getAllPostsWithUser(): List<PostDto>
}

@Repository
class PostRepositoryImpl(private val mongoTemplate: MongoTemplate) : CustomPostRepository {
    /// Janky ahh query since $match mongodb aggregation doesn't work
    override fun getAllPostsWithUser(): List<PostDto> {
        val posts = mongoTemplate.findAll<Post>()
        val users = mongoTemplate.findAll<User>()

        return posts.map { post ->
            val user = users.find { it.id == post.userId }
            PostDto (
                id = post.id,
                user = user?.toUserDto(),
                message = post.message,
                likes = post.likes,
                createdAt = post.createdAt,
                uri = post.uri
            )
        }
    }
}