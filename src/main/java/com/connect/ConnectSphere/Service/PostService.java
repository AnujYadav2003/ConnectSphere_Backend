package com.connect.ConnectSphere.Service;

import com.connect.ConnectSphere.Repository.PostRepository;
import com.connect.ConnectSphere.Repository.UserRepository;
import com.connect.ConnectSphere.model.Post;
import com.connect.ConnectSphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(Post post, Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User with id " + userId + " not found.");
        }
        post.setUser(user); // Setting the user in the post
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        List<Post> allPosts = postRepository.findAll();
        if (allPosts.isEmpty()) {
            throw new RuntimeException("No posts available.");
        }
        return allPosts;
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("No Post is present with id " + postId));
    }

    public List<Post> findPostByUserId(Long userId) {
        return postRepository.findPostByUserId(userId);
    }

    public Post updatePostById(Post updatedPost, Long postId) {
        Post existingPost = getPostById(postId);
        existingPost.setCaption(updatedPost.getCaption());
        existingPost.setImageurl(updatedPost.getImageurl());
        existingPost.setVideourl(updatedPost.getVideourl());
        return postRepository.save(existingPost);
    }

    public Post savedPost(Long postId, Long userId) {
        Post postExists = getPostById(postId);
        User userExist = userService.getUserById(userId);

        if (userExist.getSavedPost().contains(postExists)) {
            userExist.getSavedPost().remove(postExists);
        } else {
            userExist.getSavedPost().add(postExists);
        }

        userRepository.save(userExist);
        return postExists;
    }

    public Post likePost(Long postId, Long userId) {
        Post postExists = getPostById(postId);
        User userExist = userService.getUserById(userId);

        if (postExists.getLiked().contains(userExist)) {
            postExists.getLiked().remove(userExist);
        } else {
            postExists.getLiked().add(userExist);
        }

        return postRepository.save(postExists);
    }

//    public String deletePostById(Long userId, Long postId) {
//        Post postExists = getPostById(postId);
//        User userExist = userService.getUserById(userId);
//
//        if (postExists.getUser() == null) {
//            throw new RuntimeException("Post does not have an associated user.");
//        }
//
//        if (!postExists.getUser().getId().equals(userExist.getId())) {
//            throw new RuntimeException("You cannot delete other users' posts.");
//        }
//
//        postRepository.delete(postExists);
//        return "Post deleted successfully";
//    }

    public String deletePost(Long postId, Long userId) {
        Post postExists = getPostById(postId);
        if (postExists == null) {
            throw new RuntimeException("Post with id " + postId + " does not exist.");
        }

        User userExist = userService.getUserById(userId);
        if (userExist == null) {
            throw new RuntimeException("User with id " + userId + " does not exist.");
        }

        if (postExists.getUser() == null) {
            throw new RuntimeException("Post does not have an associated user.");
        }

        if (!postExists.getUser().getId().equals(userExist.getId())) {
            throw new RuntimeException("You cannot delete other users' posts.");
        }

        postRepository.delete(postExists);
        return "Post deleted successfully";
    }


}



//public Post createPost(Post post, Long userId, MultipartFile mediaFile) {
//    User user = userService.getUserById(userId);
//    if (user == null) {
//        throw new RuntimeException("User with id " + userId + " not found.");
//    }
//
//    try {
//        if (mediaFile != null && !mediaFile.isEmpty()) {
//            // Upload to Cloudinary
//            Map<?, ?> uploadResult = cloudinary.uploader().upload(mediaFile.getBytes(),
//                    ObjectUtils.asMap("resource_type", "auto"));
//
//            if (uploadResult.get("url") != null) {
//                String uploadedUrl = uploadResult.get("url").toString();
//                if (uploadResult.get("resource_type").toString().equals("image")) {
//                    post.setImageurl(uploadedUrl);
//                } else if (uploadResult.get("resource_type").toString().equals("video")) {
//                    post.setVideourl(uploadedUrl);
//                }
//            }
//        }
//    } catch (Exception e) {
//        throw new RuntimeException("Error uploading file to Cloudinary: " + e.getMessage());
//    }
//
//    post.setUser(user);
//    return postRepository.save(post);
//}