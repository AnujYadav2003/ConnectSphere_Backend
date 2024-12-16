//package com.connect.ConnectSphere.Controller;
//
//import com.connect.ConnectSphere.Service.ReelsService;
//import com.connect.ConnectSphere.Service.UserService;
//import com.connect.ConnectSphere.model.Reels;
//import com.connect.ConnectSphere.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//@RestController
//public class ReelsController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ReelsService reelsService;
//
//
//    @PostMapping("/api/reels")
//    public Reels createReels(@RequestHeader("Authorization")String jwt,
//                             @RequestBody Reels reels )
//    {
//        User user=userService.findUserByJwt(jwt);
//
//        if(user==null)
//        {
//            throw new RuntimeException("User not existing such token");
//        }
//        return  reelsService.createReels(reels,user);
//
//    }
//
//    @GetMapping("/api/reels")
//    public List<Reels> getAllReels()
//    {
//        return reelsService.getAllReels();
//    }
//
//    @GetMapping("/api/reels/user/{userId}")
//    public List<Reels> getUserReels(@PathVariable Long userId) throws Exception
//    {
//
//        return  reelsService.getUserReels(userId);
//    }
//}



package com.connect.ConnectSphere.Controller;

import com.connect.ConnectSphere.Service.ReelsService;
import com.connect.ConnectSphere.Service.UserService;
import com.connect.ConnectSphere.model.Reels;
import com.connect.ConnectSphere.model.User;
import com.connect.ConnectSphere.model.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReelsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReelsService reelsService;

    // Create Reels
    @PostMapping("/reels")
    public ResponseEntity<Response<Reels>> createReels(@RequestHeader("Authorization") String jwt,
                                                       @Valid @RequestBody Reels reels) {
        try {
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }

            Reels createdReels = reelsService.createReels(reels, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new Response<>("Reels created successfully", "success", createdReels));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Get all Reels
    @GetMapping("/reels")
    public ResponseEntity<Response<List<Reels>>> getAllReels() {
        try {
            List<Reels> reelsList = reelsService.getAllReels();
            return ResponseEntity.ok(new Response<>("Reels fetched successfully", "success", reelsList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Get Reels by user ID
    @GetMapping("/reels/user/{userId}")
    public ResponseEntity<Response<List<Reels>>> getUserReels(@PathVariable Long userId) {
        try {
            List<Reels> userReels = reelsService.getUserReels(userId);
            return ResponseEntity.ok(new Response<>("User reels fetched successfully", "success", userReels));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }
}
