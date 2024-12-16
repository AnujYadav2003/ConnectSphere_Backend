//package com.connect.ConnectSphere.Controller;
//
//import com.connect.ConnectSphere.Service.StoryService;
//import com.connect.ConnectSphere.Service.UserService;
//import com.connect.ConnectSphere.model.Story;
//import com.connect.ConnectSphere.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//public class StoryController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private StoryService storyService;
//
//    @PostMapping("/api/story")
//    public Story createStory(@RequestBody Story story,
//                             @RequestHeader("Authorization")String jwt){
//        User user=userService.findUserByJwt(jwt);
//        return storyService.createStory(story,user);
//    }
//
//    @GetMapping("/api/story/user/{userId}")
//    public List<Story> findStoryByUserId(@PathVariable Long userId,
//                                         @RequestHeader("Authorization")String jwt) throws Exception{
//        User reqUser=userService.findUserByJwt(jwt);
//        List<Story> stories=storyService.findStoryByUserId(userId);
//        return stories;
//    }
//
//}
//


package com.connect.ConnectSphere.Controller;

import com.connect.ConnectSphere.Service.StoryService;
import com.connect.ConnectSphere.Service.UserService;
import com.connect.ConnectSphere.model.Story;
import com.connect.ConnectSphere.model.User;
import com.connect.ConnectSphere.model.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private StoryService storyService;

    // Create Story
    @PostMapping("/create")
    public ResponseEntity<Response<Story>> createStory(@Valid @RequestBody Story story,
                                                       @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }

            Story createdStory = storyService.createStory(story, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new Response<>("Story created successfully", "success", createdStory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Get Stories by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response<List<Story>>> findStoryByUserId(@PathVariable Long userId,
                                                                   @RequestHeader("Authorization") String jwt) {
        try {
            User reqUser = userService.findUserByJwt(jwt);
            if (reqUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }

            List<Story> stories = storyService.findStoryByUserId(userId);
            return ResponseEntity.ok(new Response<>("Stories fetched successfully", "success", stories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }
}
