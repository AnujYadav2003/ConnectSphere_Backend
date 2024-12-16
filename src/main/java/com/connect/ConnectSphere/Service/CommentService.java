package com.connect.ConnectSphere.Service;

import com.connect.ConnectSphere.Repository.CommentRepository;
import com.connect.ConnectSphere.Repository.PostRepository;
import com.connect.ConnectSphere.model.Comment;
import com.connect.ConnectSphere.model.Post;
import com.connect.ConnectSphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    public Comment createComment(Comment comment, Long userId, Long postId) throws Exception
    {
        User user =userService.getUserById(userId);
        Post post=postService.getPostById(postId);
        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        Comment saveComment=commentRepository.save(comment);
        post.getComments().add(saveComment);
        postRepository.save(post);
        return saveComment;
    }
    public Comment likeComment(Long commentId,Long userId )
    {
        Comment comment=findCommentById(commentId);
        User user=userService.getUserById(userId);
        if(!comment.getLiked().contains(user))
        {
            comment.getLiked().add(user);
        }
        else comment.getLiked().remove(user);

        return commentRepository.save(comment);
    }
    public Comment findCommentById(Long commentId)
    {
        Optional<Comment> opt=commentRepository.findById(commentId);
        if(opt.isEmpty()){
            throw new RuntimeException("Comment not exists");
        }
        return opt.get();
    }
}
