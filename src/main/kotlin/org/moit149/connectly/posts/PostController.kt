package org.moit149.connectly.posts

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.net.URI

@RestController
@RequestMapping("/api/posts")
class PostController(private val postService: PostService) {
    @GetMapping("/all")
    fun getAllPosts() = postService.getAllPosts()

    @PostMapping
    fun createPost(
        authentication: Authentication,
        @RequestParam("message") message: String,
        @RequestParam("file") file: MultipartFile): ResponseEntity<PostDto> {
        val post = postService.createPost(authentication, message, file) ?: return ResponseEntity.badRequest().build()
        return ResponseEntity.created(URI("/api/posts/")).body(post)
    }
}
