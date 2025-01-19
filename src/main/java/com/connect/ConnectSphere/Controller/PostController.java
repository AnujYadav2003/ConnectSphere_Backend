package com.connect.ConnectSphere.Controller;

import com.connect.ConnectSphere.Service.PostService;
import com.connect.ConnectSphere.Service.UserService;
import com.connect.ConnectSphere.model.Post;
import com.connect.ConnectSphere.model.User;
import com.connect.ConnectSphere.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    // Get all posts
    @GetMapping("/posts")
    public ResponseEntity<Response<List<Post>>> getAllPosts() {
        try {
            List<Post> posts = postService.getAllPosts();
            return ResponseEntity.ok(new Response<>("Posts fetched successfully", "success", posts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Get a post by ID
    @GetMapping("/posts/{id}")
    public ResponseEntity<Response<Post>> getPostById(@PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            if (post == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new Response<>("Post not found", "error", null));
            }
            return ResponseEntity.ok(new Response<>("Post fetched successfully", "success", post));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Get posts by user ID
    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<Response<List<Post>>> findPostByUserId(@PathVariable Long userId) {
        try {
            List<Post> posts = postService.findPostByUserId(userId);
            return ResponseEntity.ok(new Response<>("Posts fetched successfully", "success", posts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Update post by ID
    @PutMapping("/posts/{id}")
    public ResponseEntity<Response<Post>> updatePostById(@RequestBody Post post, @PathVariable Long id) {
        try {
            Post updatedPost = postService.updatePostById(post, id);
            return ResponseEntity.ok(new Response<>("Post updated successfully", "success", updatedPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Save a post
    @PutMapping("/posts/save/{postId}")
    public ResponseEntity<Response<Post>> savePost(@PathVariable Long postId,
                                                   @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }
            Post savedPost = postService.savedPost(postId, user.getId());
            return ResponseEntity.ok(new Response<>("Post saved successfully", "success", savedPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Like a post
    @PutMapping("/posts/like/{postId}")
    public ResponseEntity<Response<Post>> likePost(@PathVariable Long postId,
                                                   @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }
            Post likedPost = postService.likePost(postId, user.getId());
            return ResponseEntity.ok(new Response<>("Post liked successfully", "success", likedPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Delete a post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Response<String>> deletePost(@RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long postId) {
        try {
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }
            String responseMessage = postService.deletePost(postId, user.getId());
            return ResponseEntity.ok(new Response<>(responseMessage, "success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Create a new post
    @PostMapping("/posts")
    public ResponseEntity<Response<Post>> createPost(@RequestHeader("Authorization") String jwt,
                                                     @RequestBody Post post) {
        try {
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }
            Post createdPost = postService.createPost(post, user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new Response<>("Post created successfully", "success", createdPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }
}



//public ResponseEntity<Response<Post>> createPost(
//        @RequestHeader("Authorization") String jwt,
//        @RequestPart(value = "post") Post post,
//        @RequestPart(value = "mediaFile", required = false) MultipartFile mediaFile
//) {
//    try {

//        User user = userService.findUserByJwt(jwt);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
//                    new Response<>("Invalid or expired JWT", "error", null));
//        }
//

//        Post createdPost = postService.createPost(post, user.getId(), mediaFile);
//

//        return ResponseEntity.status(HttpStatus.CREATED).body(
//                new Response<>("Post created successfully", "success", createdPost));
//    } catch (Exception e) {

//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                new Response<>(e.getMessage(), "error", null));
//    }
//}
