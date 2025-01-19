
package com.connect.ConnectSphere.Controller;

import com.connect.ConnectSphere.Service.CommentService;
import com.connect.ConnectSphere.Service.UserService;
import com.connect.ConnectSphere.model.Comment;
import com.connect.ConnectSphere.model.User;
import com.connect.ConnectSphere.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    // Create a new comment on a post
    @PostMapping("/comments/post/{postId}")
    public ResponseEntity<Response<Comment>> createComment(
            @RequestBody Comment comment,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long postId) {
        try {
            // Find user by JWT
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }

            // Create comment
            Comment createdComment = commentService.createComment(comment, postId, user.getId());

            // Return the success response
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new Response<>("Comment created successfully", "success", createdComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Like a comment
    @PutMapping("/like/post/{commentId}")
    public ResponseEntity<Response<Comment>> likeComment(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long commentId) {
        try {
            // Find user by JWT
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }

            // Like the comment
            Comment likedComment = commentService.likeComment(commentId, user.getId());

            // Return the success response
            return ResponseEntity.ok(new Response<>("Comment liked successfully", "success", likedComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Find a comment by its ID
    @GetMapping("/findcommentbyid/{commentId}")
    public ResponseEntity<Response<Comment>> findCommentById(@PathVariable Long commentId) {
        try {
            Comment com = commentService.findCommentById(commentId);
            if (com == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new Response<>("Comment not found", "error", null));
            }

            // Return the success response
            return ResponseEntity.ok(new Response<>("Comment fetched successfully", "success", com));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }
}
