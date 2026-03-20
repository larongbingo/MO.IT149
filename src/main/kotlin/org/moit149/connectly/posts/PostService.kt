package org.moit149.connectly.posts

import kotlinx.coroutines.runBlocking
import org.moit149.connectly.posts.comments.CommentRepository
import org.moit149.connectly.users.UserService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userService: UserService,
    private val blobStorage: BlobStorage) {
    fun getAllPosts() = postRepository.findAll()

    fun createPost(authentication: Authentication, message: String, file: MultipartFile? = null): PostDto? {
        var uri: String? = null
        if (file != null) {
            runBlocking {
                uri = blobStorage.upload(file.bytes, file.originalFilename ?: "")
            }
        }

        val user = userService.getConnectlyProfile(authentication) ?: return null
        val newPost = Post(
            id = null,
            userId = user.id.toString(),
            message = message,
            uri = uri
        )
        val postEntity = postRepository.save(newPost)
        return PostDto(
            id = postEntity.id.toString(),
            user = user.toUserDto(),
            message = postEntity.message,
            likes = postEntity.likes,
            createdAt = postEntity.createdAt,
            uri = postEntity.uri
        )
    }

    fun createComment() {

    }
}