package org.moit149.connectly.posts

import org.moit149.connectly.posts.comments.CommentRepository
import org.moit149.connectly.users.UserService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userService: UserService,
    private val commentRepository: CommentRepository) {
    fun getAllPosts() = postRepository.findAll()

    fun createPost(createPostDto: CreatePostDto, authentication: Authentication): PostDto? {
        val user = userService.getConnectlyProfile(authentication) ?: return null
        val newPost = Post(
            id = null,
            userId = user.id.toString(),
            message = createPostDto.message
        )
        val postEntity = postRepository.save(newPost)
        return PostDto(
            id = postEntity.id.toString(),
            user = user.toUserDto(),
            message = postEntity.message,
            likes = postEntity.likes,
            createdAt = postEntity.createdAt
        )
    }

    fun createComment() {

    }
}